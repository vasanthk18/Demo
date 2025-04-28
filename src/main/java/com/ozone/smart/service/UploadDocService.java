package com.ozone.smart.service;

import java.io.File;
import java.util.Base64;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.DocSubmission;
import com.ozone.smart.entity.Guarantor;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.DocSubmitRepo;
import com.ozone.smart.repository.GuarantorRepo;
import com.ozone.smart.util.FileUploadUtil;
import com.ozone.smart.util.LoadFileUtil;
import com.ozone.smart.util.TimeStampUtil;

import jakarta.servlet.ServletOutputStream;

import org.springframework.util.StringUtils;

@Service
public class UploadDocService {
	
    @Autowired
    private AmazonS3 amazonS3;
    
	@Autowired
	private DocSubmitRepo docSubmitRepo;
	
	@Autowired
	private GuarantorRepo guarantorRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
    @Value("${s3.bucket.name}")
    private String bucketName;
	
	public Response<String> addDoc(String username, String custId, MultipartFile multipartFile) {
//		final String UPLOAD_DIRECTORY = "src/main/resources/Documents/";

//		String msg = "";
		String name = "";
//		String strIdfldname = "";
//		String strQuery = "";
//		String strUploadatetime = "";
		String strUploaduser = "";

		TimeStampUtil gts = new TimeStampUtil();
		String Uploadatetime = gts.TimeStamp();
		Uploadatetime = Uploadatetime.trim();
		Response<String> response = new Response<>();
		try {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                name = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                name = custId + name; // Added underscore for clarity
                try {
                    List<Guarantor> guarantor = guarantorRepo.findGuarantorsByDocSubmissionCustId(custId);
                    if (guarantor.size() != 0) {
                        // Convert MultipartFile to File
                        File convFile = convertMultiPartToFile(multipartFile);
                        // Upload file to S3
                        amazonS3.putObject(new PutObjectRequest(bucketName, name, convFile));

                        // Delete the local file
                        convFile.delete();

                        int inUser = docSubmitRepo.updateDocSubmission(name, Uploadatetime, strUploaduser, custId);
                        if (inUser == 1) {
                            response.setData("File " + name + " uploaded successfully");
                            inUser = customerRepo.updateCustomerDetailsDcUpload(custId);
                        } else {
                            response.setData("Error updating document submission for " + custId);
                        }

                    } else {
                        response.setData("Upload aborted, please update document submission for customer " + custId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setData("File upload failed due to " + e);
                }
            } else {
                response.setData("No file uploaded");
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
            response.setData("File upload failed due to " + ex);
        }
		return response;
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
	
	
	public ResponseEntity<InputStreamResource> fetchDoc(String custId, String flag) throws IOException {
        String strCustomer = custId;
        String strFilename = "";
        String strFlag = flag;

        HttpHeaders headers = new HttpHeaders();
        InputStreamResource resource1 = null;
System.out.println(custId);
        Response<String> response = new Response<>();
        if (strCustomer == null || strCustomer.isEmpty()) {
            response.setData("Please select customer id");
            return ResponseEntity.badRequest().body(null);
        } else {
            try {
                Optional<DocSubmission> opdcsubmit = docSubmitRepo.findById(strCustomer);

                if (opdcsubmit.isPresent()) {
                    DocSubmission ds = opdcsubmit.get();
                    strFilename = ds.getFilename();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(null);
            }

            if (strFlag.equals("cqc")) {
                if (strFilename == null || strFilename.equals("null")) {
                    strFilename = "";
                }
                response.setData(strFilename);
                return ResponseEntity.ok().body(null);
            } else if (strFlag.equals("file")) {
                if (!StringUtils.isEmpty(strFilename)) {
                    try {
                        // Get the file from S3
                        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, strFilename));
                        resource1 = new InputStreamResource(s3Object.getObjectContent());
                        System.out.println(resource1.getFilename());
                        // Set headers
                        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + strFilename);
                        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

                        return ResponseEntity.ok()
                                .headers(headers)
                                .contentLength(s3Object.getObjectMetadata().getContentLength())
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(resource1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        headers.add("x-message", "Error fetching file from S3: " + e.getMessage());
                        return ResponseEntity.status(500).headers(headers).body(null);
                    }
                } else {
                    headers.add("x-message", "Customer file is not uploaded");
                    return ResponseEntity.status(404).headers(headers).body(null);
                }
            } else {
                headers.add("x-message", "Invalid flag provided");
                return ResponseEntity.badRequest().headers(headers).body(null);
            }
        }
    }
}
