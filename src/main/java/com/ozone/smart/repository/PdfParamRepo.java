package com.ozone.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.pdfParams;

@Repository
public interface PdfParamRepo extends JpaRepository<pdfParams, String> {

}
