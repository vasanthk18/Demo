package com.ozone.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.repository.CollectionRepo;

@Service
public class BankTransferService {
	
	@Autowired
	CollectionRepo coll;
	
	public String transferStatus(String fromDate, String toDate) {
		int status = -1;
		String success = null ;
		try {
			status = coll.updateTransferStatus(fromDate, toDate);
		}catch(Exception e){
			success = "There is no entry for the specified Date";
			e.printStackTrace();	
		}
		
		if(status != 0) {
			success = "Status Changed";
		}else {
			success = "Status Already changed";
		}

		return success;
	}

}
