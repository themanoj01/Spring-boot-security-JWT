package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
