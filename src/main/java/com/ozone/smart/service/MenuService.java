package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.entity.Category;
import com.ozone.smart.entity.Profile;
import com.ozone.smart.repository.CategoryJdbcRepo;
import com.ozone.smart.repository.CategoryRepo;
import com.ozone.smart.repository.ProfileRepo;

@Service
public class MenuService {
	
	@Autowired
	private ProfileRepo profileRepo;
	
	@Autowired
	private CategoryRepo catRepo;
	
	@Autowired
	private CategoryJdbcRepo catJdbcRepo;
	
	
	public List<Map<String, Object>> getCategory(String profile) {
		
		String strProfile = profile;
		String strCategory = "";
		String strQuery = "";
		

//		strQuery = "From Profile where profile = " + "'" + strProfile + "'";
		List<Profile> profiles = profileRepo.findByProfileName(strProfile);

		int i = 0;
		int j = profiles.size();
		j = j - 1;
		strQuery = "";
		for (Profile pro : profiles) {
			strCategory = pro.getCategory();
			if (i < j) {
				strQuery += "'" + strCategory + "',";
			} else {
				strQuery += "'" + strCategory + "')";
				strQuery = "SELECT * FROM tblcategory WHERE parentcategory in (" + strQuery;
			}
			i++;
		}

		strQuery += "ORDER by id";
		List<Category> categories = null;
		try {
		categories = catJdbcRepo.findByProfileCategories(strQuery);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		// Group categories by parentcategory
	    Map<String, List<String>> categoryGroups = new HashMap<>();
	    for (Category category : categories) {
	        String parentCategory = category.getParentcategory();
	        String categoryName = category.getCategoryname();
	        categoryGroups.computeIfAbsent(parentCategory, k -> new ArrayList<>()).add(categoryName);
	    }
	    
	    List<Map<String, Object>> result = new ArrayList<>();
	    for (Map.Entry<String, List<String>> entry : categoryGroups.entrySet()) {
	        Map<String, Object> categoryInfo = new HashMap<>();
	        categoryInfo.put("parentcategory", entry.getKey());
	        categoryInfo.put("categoryname", entry.getValue());
	        result.add(categoryInfo);
	    }
	    
	    
		return result;
	}

}
