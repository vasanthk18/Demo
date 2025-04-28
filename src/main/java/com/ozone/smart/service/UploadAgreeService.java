package com.ozone.smart.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.Guarantor;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.util.FileUploadUtil;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class UploadAgreeService {
	
	@Autowired
	private CustomerVehicleRepo customerVehicleRepo;
	
	 @Autowired
	    private AmazonS3 amazonS3;
	 
	 @Value("${s3.bucket.name}")
	    private String bucketName;

	public Response<String> viewAgreement(String custId, String flag) {
		
		Response<String> response = new Response<>();
		
		String strMsg = "";
		String strCustomer = "";
		String strAgreements[];
		String strFlag = "";
		String strQuery = "";
		
		int intAgreement = 0;
		String strAgreement = "";
		String strProposalno = "";
		
		strCustomer =custId;
		strFlag = flag;
		
		if (strCustomer == null || strCustomer.length() == 0) {			
			strMsg = "";
		} else {
			try {
				List<CustomerVehicle> custveh = customerVehicleRepo.findFilteredCustomerVehicles(strCustomer, strFlag);
				
				strAgreements = new String[custveh.size()];
						
				int i = 0;
				for (CustomerVehicle cv:custveh) {
					strProposalno = cv.getProposalno();
					intAgreement = cv.getAgreementserial();
					strAgreement = "AN" + Integer.toString(intAgreement);
					strAgreements[i] = strProposalno + "::" + strAgreement;
					i++;
				}	
				response.setData(Arrays.toString(strAgreements));	
						
			} catch (Exception e) {
				e.printStackTrace();
				response.setData("Error retrieving agreements: " + strCustomer);
			}			
		}
		return response;
	}

	 public Response<String> addAgree(String username, String agreeId, MultipartFile multipartFile) {
	        String name = "";
	        String strAgreement = "";
	        String strAgreementName = "";
	        String strAgreementserial = "";
	        String strAGUPRequestdatetime = "";
	        String strLoginuser = username;

	        Response<String> response = new Response<>();

	        TimeStampUtil gts = new TimeStampUtil();
	        strAGUPRequestdatetime = gts.TimeStamp();

	        if (multipartFile != null && !multipartFile.isEmpty()) {
	            try {
	                strAgreementName = multipartFile.getOriginalFilename();
	                strAgreement = agreeId;
	                String[] arrOfStr = strAgreement.split("::");
	                strAgreement = arrOfStr[1];

	                strAgreementName = strAgreement + strAgreementName;
	                strAgreementserial = strAgreement.replace("AN", "");

	                int agreementSerial = Integer.parseInt(strAgreementserial);

	                try {
	                    List<CustomerVehicle> custveh = customerVehicleRepo.findByagreementserial(agreementSerial);
	                    if (custveh.size() != 0) {
	                        // Convert MultipartFile to File
	                        File convFile = convertMultiPartToFile(multipartFile);

	                        // Use TransferManager for multipart upload
	                        TransferManager transferManager = TransferManagerBuilder.standard()
	                                .withS3Client(amazonS3)
	                                .build();

	                        try {
	                            Upload upload = transferManager.upload(bucketName, strAgreementName, convFile);
	                            upload.waitForCompletion();
	                        } catch (InterruptedException e) {
	                            e.printStackTrace();
	                            response.setData("File upload failed due to " + e);
	                            return response;
	                        }

	                        // Delete the local file
	                        convFile.delete();

	                        int intUser = customerVehicleRepo.updateAgreementInfo(strAgreementName, strLoginuser,
	                                strAGUPRequestdatetime, agreementSerial);
	                        if (intUser == 1) {
	                            response.setData("File " + strAgreementName + " uploaded successfully");
	                        } else {
	                            response.setData("Error updating agreement for " + strAgreement);
	                        }

	                    } else {
	                        response.setData("Upload aborted, please allocate vehicle to customer ");
	                    }
	                } catch (Exception e) {
	                    response.setData("File upload failed due to " + e);
	                }

	            } catch (Exception ex) {
	                response.setData("File upload failed due to " + ex);
	            }
	        } else {
	            response.setData("Sorry this servlet only handles file upload request");
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

}
