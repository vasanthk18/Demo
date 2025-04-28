package com.ozone.smart.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.component.AESEncryptComponent;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.User;
import com.ozone.smart.entity.UserAudit;
import com.ozone.smart.repository.LoginRepo;
import com.ozone.smart.repository.UserRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class LoginService {

	@Autowired
	private LoginRepo userAuditRepo;
	
	@Autowired
	private AESEncryptComponent AESComponent;
	
	@Autowired
	private UserRepo userRepo;
	
	public Response<String> checkUser(String username, String password) {
		
		Response<String> response = new Response<>();
		
		Optional<User> Opuser = userRepo.findById(username);
		if(Opuser.isPresent()) {
			User user = Opuser.get();
			String Password = user.getPassword();
			Password = Password.replaceAll("[\\n\\t]", "");
			
			String DecryptedPassword = AESComponent.encrypt(password, username);
			if (DecryptedPassword.equals(Password)) {
				String Fullname = user.getFullname();
				String Profile = user.getProfile();
				
				TimeStampUtil ts = new TimeStampUtil();		
				String Logindatetime = ts.newfmtTimeStamp();
				
				String userDetails = Logindatetime+"|"+Fullname+"|"+Profile+"|"+username;
				
				response.setData(userDetails);
//				HttpSession session = request.getSession();
//		        // put the request parameter into the session
//		        session.setAttribute("fwdUser", username);
				
//				url = "/Main.jsp";
				
				user.setLoggedin(1);
				User usr = userRepo.save(user);
				
				if (usr !=null) {
					System.out.println("User log in updated successfully");
				}
				
				UserAudit ua = new UserAudit();
				ua.setUserid(username);
				ua.setLogindatetime(Logindatetime);
				ua.setLogoutdatetime("");
							
				try {
					userAuditRepo.save(ua);
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
				}	
				
			} else {
				response.setErrorMsg("error Invalid credentials");
			}	
			
		}else {
			response.setErrorMsg("error Invalid credentials");
		}
		
		return response;
	}
	


}
