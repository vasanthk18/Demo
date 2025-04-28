package com.ozone.smart.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.Response;
import com.ozone.smart.service.StartOfDayService;

@RestController
@RequestMapping("/api/v1")
public class StartOfDayController {
	
	@Autowired
	private StartOfDayService startOfDayService;
	
	@PostMapping("/sodp")
	public Response<String> startOfDayRun(@RequestBody Map<String, String> payload){
//		String todate = payload.get("todate");
        String username = payload.get("username");
        return startOfDayService.startOfDayRun(username);
	}
	
	 @GetMapping("/check-date")
	    public String checkDate() {
	        return startOfDayService.checkIfDateExists();
	    }

}
