package com.ozone.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.ReleaseLetter;

@Repository
public interface ReleaseLetterRepo extends JpaRepository<ReleaseLetter, String> {

}
