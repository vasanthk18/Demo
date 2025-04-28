package com.ozone.smart.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Category;

@Repository
public class CategoryJdbcRepo {
	

	private JdbcTemplate template;
	
	
	public JdbcTemplate getTemplate() {
		return template;
	}

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}


    public List<Category> findByProfileCategories(String query) {
        // Execute the query and map the result to Category objects using BeanPropertyRowMapper
        List<Category> categories = template.query(query, new BeanPropertyRowMapper<>(Category.class));
        return categories;
    }

}
