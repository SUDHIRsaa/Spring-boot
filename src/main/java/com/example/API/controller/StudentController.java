package com.example.API.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.API.Repository.StudentRepository;
import com.example.API.Response.ResponseBean;
import com.example.API.entity.Student;


@RestController

public class StudentController {
   
    

    @Autowired
    public StudentRepository studentrepo;

    @Autowired
    public ResponseBean response;


    @GetMapping(path="/students",produces = "application/json")
        public ResponseEntity<Object> getStudents(){

            List<Student> studentList=studentrepo.findAll();
            response.setData(studentList);
            return new ResponseEntity<>(response,HttpStatus.OK);
            
        }

        @GetMapping(path="/students/{uid}",produces = "application/json")
        public ResponseEntity<Object> getUid(@PathVariable String uid){

            Optional<Student>student=studentrepo.findById(uid);
            response.setData(student.get());
            return new ResponseEntity<>(response,HttpStatus.OK);
            
        }
       @DeleteMapping(path="/students/{uid}")
public ResponseEntity<Object> deleteStudent(@PathVariable String uid) {
    try {
        studentrepo.deleteById(uid);
        response.setErrorCode(null);
        response.setData(null);
        response.setData("Student with id " + uid + " deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
        response.setErrorCode("ERR001");
        response.setData("Error deleting student: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PutMapping(path="/students/{uid}", produces = "application/json")
public ResponseEntity<Object> updateStudent(@PathVariable String uid, @RequestBody Student updatedStudent) {
    try {
        if (!studentrepo.existsById(uid)) {
            response.setErrorCode("ERR002");
            response.setData("Student with id " + uid + " not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Set the UID of the updated student to the provided UID
        updatedStudent.setUid(uid);

        // Save the updated student record
        studentrepo.save(updatedStudent);
        
        response.setErrorCode(null);
        response.setData(updatedStudent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
        response.setErrorCode("ERR003");
        response.setData("Error updating student: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PatchMapping(path="/students/{uid}", produces = "application/json")
public ResponseEntity<Object> patchStudent(@PathVariable String uid, @RequestBody Map<String, Object> updates) {
    try {
        Optional<Student> existingStudentOpt = studentrepo.findById(uid);
        
        if (!existingStudentOpt.isPresent()) {
            response.setErrorCode("ERR002");
            response.setData("Student with id " + uid + " not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        Student existingStudent = existingStudentOpt.get();
        
        // Update fields with the provided values
        if (updates.containsKey("classname")) {
            existingStudent.setClassname((String) updates.get("classname"));
        }
        if (updates.containsKey("name")) {
            existingStudent.setName((String) updates.get("name"));
        }
        if (updates.containsKey("Email")) {
            existingStudent.setEmail((String) updates.get("Email"));
        }
        if (updates.containsKey("Contact")) {
            existingStudent.setContact((String) updates.get("Contact"));
        }
        if (updates.containsKey("Batch_No")) {
            existingStudent.setBatch_No((String) updates.get("Batch_No"));
        }

        // Save the partially updated student record
        studentrepo.save(existingStudent);
        
        response.setErrorCode(null);
        response.setData(existingStudent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
        response.setErrorCode("ERR004");
        response.setData("Error updating student: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PostMapping(path="/students",consumes = "application/json", produces = "application/json")
public ResponseEntity<Object> createStudent(@RequestBody Student newStudent) {
    try {
        // Check if a student with the same UID already exists
        if (studentrepo.existsById(newStudent.getUid())) {
            response.setErrorCode("ERR005");
            response.setData("Student with id " + newStudent.getUid() + " already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        // Save the new student record
        Student savedStudent = studentrepo.save(newStudent);
        
        response.setErrorCode(null);
        response.setData(savedStudent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
        response.setErrorCode("ERR006");
        response.setData("Error creating student: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}



        

    } 
  




