package com.ozone.smart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Category;
import com.ozone.smart.service.MenuService;

@RestController
@RequestMapping("/api/v1/menuItems")
public class MenuServlet {
	
	@Autowired
	private MenuService menuService;
	
	
	@GetMapping()
	public List<Map<String, Object>> getCategory(@RequestParam String profile){
		return menuService.getCategory(profile);
	}

}
