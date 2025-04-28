package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.ProposalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.Reposession;
import com.ozone.smart.entity.VehicleMaster;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.repository.ReposessionRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.formatDigits;

@Service
public class ProposalService {

	@Autowired
	private ProposalRepo proposalRepo;

	@Autowired
	private ReposessionRepo reposessionRepo;

	public Response<String> saveProposal(ProposalDto proposalDto) {
		String strProposal = "";
		String strRequestdatetime = "";
		String strTruncreqdatetime = "";
		String strDate = "";
//		String strAgreement = "";
		String strAgreements[];
		String strDiscount = "";

		double dblAmount = 0;
		int intAmount = 0;

		String CustomerId = proposalDto.getCustomerid();
		String VehicleRegNo = proposalDto.getVehicleregno();
		String Amount = proposalDto.getAmount();
		String DwnAmount = proposalDto.getDownamount();
		String LoginUser = proposalDto.getCaptureuser();
		String strAgreement = proposalDto.getAgreement();
		String flag = proposalDto.getFlag();

		TimeStampUtil gts = new TimeStampUtil();
		strRequestdatetime = gts.TimeStamp();
		strRequestdatetime = strRequestdatetime.trim();
		strDate = gts.standardDate();

		Response<String> response = new Response<>();

		if (flag == null || flag.isEmpty()) {
			List<VehicleMaster> Listvm = proposalRepo.findByBrandModelCcColor(VehicleRegNo);
			for (VehicleMaster vehm : Listvm) {
				strDiscount = vehm.getDiscount();
			}
		} else {
			strDiscount = "0";
		}

		if (Amount.contains(",")) {
			Amount = Amount.replace(",", "");
		}

		dblAmount = Double.parseDouble(Amount);
		intAmount = (int) Math.round(dblAmount);
		Amount = Integer.toString(intAmount);

		formatDigits fd = new formatDigits();
		Amount = fd.digit(Amount);
		Proposal prop = new Proposal();
		prop.setCustomerid(CustomerId);
		prop.setVehicleregno(VehicleRegNo);
		prop.setAmount(Amount);
		prop.setDownamount(DwnAmount);
		prop.setDiscount(strDiscount);
		prop.setCapturedatetime(strRequestdatetime);
		prop.setCaptureuser(LoginUser);

		try {
			proposalRepo.save(prop);
			try {
				List<Proposal> Lp = proposalRepo.findByCustIdAndCaptureDT(CustomerId, strRequestdatetime);
				for (Proposal propsl : Lp) {
					strProposal = propsl.getProposalno();
				}

				response.setData("Proposal id : " + strProposal + " created successfully." + "|" + strDate);

				// Update reposession
				if (strAgreement != null) {
					strAgreements = strAgreement.split(" | ");
					strAgreement = strAgreements[0];

					Optional<Reposession> Opreposess = reposessionRepo.findById(strAgreement);
					if (Opreposess.isPresent()) {
						Reposession repo = Opreposess.get();
						repo.setNewproposalid(strProposal);
						Reposession reposess = reposessionRepo.save(repo);
						if (reposess != null) {
							System.out.println(
									"Reposession for : " + strAgreement + " allocated new proposal " + strProposal);
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				e.printStackTrace();
				response.setErrorMsg("Error creating proposal for : " + CustomerId);
				response.setErrorMsg("Error creating proposal for : " + CustomerId);
			}

		} catch (Exception e) {
			Throwable th = e.getCause();
//	        System.out.println("THROWABLE INFO: " + th.getCause().toString());
//	        System.out.println("THROWABLE INFO: " + th.getCause().toString());
			e.printStackTrace();
			String Msg = th.getCause().toString();
			if (Msg.contains(
					"Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1")
					|| Msg.contains("The database returned no natively generated identity value")) {

				try {
					strTruncreqdatetime = strRequestdatetime.substring(0, 16) + '%';

					List<Proposal> proposal = proposalRepo.findByCustIdAndCaptureDTLike(CustomerId,
							strTruncreqdatetime);
					for (Proposal propsl : proposal) {
						strProposal = propsl.getProposalno();
					}
					response.setData("Proposal id : " + strProposal + " created successfully." + "|" + strDate);

					// Update reposession
					if (strAgreement != "") {
						strAgreements = strAgreement.split(" | ");
						strAgreement = strAgreements[0];

						Optional<Reposession> Opreposess = reposessionRepo.findById(strAgreement);
						if (Opreposess.isPresent()) {
							Reposession repo = Opreposess.get();
							repo.setNewproposalid(strProposal);
							Reposession reposess = reposessionRepo.save(repo);
							if (reposess != null) {
								System.out.println(
										"Reposession for : " + strAgreement + " allocated new proposal " + strProposal);
							}
						}
					}

				} catch (Exception ex) {
					System.out.println(ex.getLocalizedMessage());
					response.setErrorMsg("Error creating proposal for : " + CustomerId);
					response.setErrorMsg("Error creating proposal for : " + CustomerId);
				}

			} else if (Msg.contains("")) {
				response.setErrorMsg("General error");
				response.setErrorMsg("General error");
			}
		}

		return response;
	}

	public Response<List<ProposalDto>> viewProposal() {

//		strQuery = "Select distinct customerid From Proposal where proposalno in (select proposalno from Impoundedstock) order by customerid";
		List<String> proposal = proposalRepo.findDistinctCustomerIdWithImpoundedStock();
		Response<List<ProposalDto>> response = new Response<>();
		List<ProposalDto> propDto = new ArrayList<>();
		for (String p : proposal) {
			ProposalDto pDto = new ProposalDto();
			pDto.setCustomerid(p);
			propDto.add(pDto);
		}
		response.setData(propDto);
		return response;
	}

//	public Response<ProposalDto> viewProposals() {
//		
//		
//		
//		return null;
//	}
//(load proposal)
	public Response<String> viewProposals(String custId, String flag) {
		Response<String> response = new Response<>();

		String strMsg = "";
		String strCustomer = "";
		String strProposal[];
		String strProposalno = "";
		String strFlag = "";

		strCustomer = custId;
		strFlag = flag;

		if (strCustomer == null || strCustomer.length() == 0) {
			strMsg = "";
		} else {

			try {
				List<Proposal> proposal = proposalRepo.findByCustomerWithFlag(strCustomer, strFlag);
				strProposal = new String[proposal.size()];

				int i = 0;
				for (Proposal prop : proposal) {
					strProposalno = prop.getProposalno();
					strProposal[i] = strProposalno;
					i++;
				}

				response.setData(Arrays.toString(strProposal));

			} catch (Exception e) {
				e.printStackTrace();
				response.setErrorMsg("Error retrieving customer id: " + strCustomer);
				response.setErrorMsg("Error retrieving customer id: " + strCustomer);
			}
		}
		return response;
	}

	public Response viewPropArgs() {
		List<Proposal> prop = proposalRepo.findProposalNotInLoan();
		Response res = new Response();
		res.setData(prop);
		return res;
	}

	public Response<List<ProposalDto>> viewCustomerArgs(String flag) {
		Response<List<ProposalDto>> res = new Response<>();
		if (flag.equals("PartCashReceipt")) {
			List<String> cdList = proposalRepo.custNameForPartCashReci();
			List<ProposalDto> cDt = new ArrayList<>();
			for (String cusd : cdList) {
				ProposalDto cusDdto = new ProposalDto();
				cusDdto.setCustomerid(cusd);
				cDt.add(cusDdto);
			}
			res.setData(cDt);
		}
		return res;
	}

//	public Response<String> (String custId) {
//		Response<String> response = new Response<>();
//		
//		String strMsg = "";
//		String strCustomer = "";
//		String strProposal[];
//		String strProposalno = "";
//		
//		strCustomer = custId;
//		
//		if (strCustomer == null || strCustomer.length() == 0) {			
//			strMsg = "";
//		} else {
//			
//						
//			try {
//				Proposal proposal = proposalRepo.getLatestBikeProposalByCustId(strCustomer);
//				strProposal = new String[proposal.size()];
//				
//				int i = 0;
//				for (Proposal prop:proposal) {
//					strProposalno = prop.getProposalno();
//					strProposal[i] = strProposalno;
//					i++;
//				}	
//				
//				response.setData(Arrays.toString(strProposal));	
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				response.setErrorMsg("Error retrieving customer id: " + strCustomer);
//				response.setErrorMsg("Error retrieving customer id: " + strCustomer);
//			}			
//		}	
//		return response;
//	}
//	

	public Response<ProposalDto> getProposalsBikeName(String custId) {

		String strCustomer = "";

		strCustomer = custId;
		Proposal proposal = proposalRepo.getLatestBikeProposalByCustId(strCustomer);

		Response<ProposalDto> response = new Response<>();

		if (proposal != null) {
			ProposalDto proposalDto = new ProposalDto();

			proposalDto.setVehicleregno(proposal.getVehicleregno());
			proposalDto.setDownamount(proposal.getDownamount());

			response.setData(proposalDto);
			response.setResponseCode(200); // HTTP OK
			response.setErrorMsg(null); // No error

		} else {
			response.setData(null);
			response.setResponseCode(404); // HTTP Not Found
			response.setErrorMsg("No proposal found for the given customer ID");
		}

		return response;
	}

}