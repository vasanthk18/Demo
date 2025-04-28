package com.ozone.smart.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
	public static final JdbcTemplate template = new JdbcTemplate();
	
//	select distinct parentcategory From Category where parentcategory is not null order by parentcategory
	
	@Query("SELECT DISTINCT c.parentcategory FROM Category c WHERE c.parentcategory IS NOT NULL ORDER BY c.parentcategory")
	List<String> getParentCategory();
	
	@Query("SELECT c FROM Category c WHERE c.categoryname IN :categories OR c.parentcategory IN :categories")
    List<Category> findByProfileCategories(@Param("categories") List<String> categories);

	
	

}
