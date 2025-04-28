package com.ozone.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.Response;
import com.ozone.smart.repository.LoginRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class LogoutService {
	
	@Autowired
	private LoginRepo loginRepo;

	public Response<String> logout(String userId, String inTime) {
		
		String strUserid, strLogintime, strLogouttime;
		
		String url = "/Login.jsp";
		String strMsg = "";
		String strQuery = "";
		
		strUserid = userId;
		strLogintime = inTime;
		
		Response<String> response = new Response<>();
			
		if (strUserid == null || strUserid.length() == 0) {
			
			System.out.println("Error updating useraudit: missing login user");
			
		} else {
			
			TimeStampUtil gts = new TimeStampUtil();		
			strLogouttime = gts.newfmtTimeStamp();
													
													
			int intUser = loginRepo.updateUserAudit(strUserid, strLogintime, strLogouttime);
			if (intUser == 1) {
				strMsg = "Updated user audit for user : " + strUserid + " successfully";
				System.out.println(strMsg);
			}
		}
		response.setData(strMsg);
		return response;
	}

}
