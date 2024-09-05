package com.example.API.controller;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        public ResponseEntity<Object> deleteStudent(@PathVariable String uid){
            studentrepo.deleteById(uid);
            response.setErrorCode(null);
            response.setData(null);
            response.setData("Student with id "+uid+" deleted successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        

    } 
  




