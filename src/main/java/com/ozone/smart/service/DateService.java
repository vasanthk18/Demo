package com.ozone.smart.service;

import org.springframework.stereotype.Service;

import com.ozone.smart.dto.Response;
import com.ozone.smart.util.GetFutureDate;

@Service
public class DateService {

	public String getDate() {
//		Response<String> response = new Response<>();
		String newDate = "";
		String passchangedate = "";
		
		GetFutureDate gfd = new GetFutureDate();
		newDate = gfd.newDate(0, 0, 0, 0, 0, 0);
		
//		response.setData(newDate);
//		request.setAttribute("paydate", newDate);	
		
		GetFutureDate newgfd = new GetFutureDate();
		passchangedate = newgfd.newDate(1, 0, 0, 0, 0, 0);
		String date =newDate +"|"+ passchangedate;
//		response.setData(date);
		return date;
	}
	

}
