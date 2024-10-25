package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.model.Student;
import com.example.SpringSecurity.model.User;
import com.example.SpringSecurity.repository.StudentRepository;
import com.example.SpringSecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final StudentRepository studentRepository;
    public AdminController(StudentRepository studentRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }
    @PostMapping("/register")
    public User adminRegister(@RequestBody User user) {
        return userService.registerAdmin(user);
    }

    @PostMapping("/login")
    public String adminLogin(@RequestBody User user) {
        return userService.verify(user);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Optional<Student> existingOne = studentRepository.findById(id);
        if(existingOne.isPresent()) {
            Student existingStudent = existingOne.get();
            existingStudent.setName(student.getName());
            existingStudent.setMarks(student.getMarks());
            studentRepository.save(existingStudent);
            return ResponseEntity.ok("Student updated successfully!");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(@PathVariable int id) {
        studentRepository.deleteById(id);
    }
}
