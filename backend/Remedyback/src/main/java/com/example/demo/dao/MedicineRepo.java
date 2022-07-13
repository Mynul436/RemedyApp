package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Medicine;

public interface MedicineRepo extends JpaRepository<Medicine,String> {
    
}
