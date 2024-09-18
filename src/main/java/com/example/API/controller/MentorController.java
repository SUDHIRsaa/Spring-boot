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

import com.example.API.Repository.MentorRepository;
import com.example.API.Response.ResponseBean;
import com.example.API.entity.Mentor;

@RestController
public class MentorController {

    @Autowired
    public MentorRepository mentorRepo;

    @Autowired
    public ResponseBean response;

    @GetMapping(path = "/mentors", produces = "application/json")
    public ResponseEntity<Object> getMentors() {
        List<Mentor> mentorList = mentorRepo.findAll();
        response.setData(mentorList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/mentors/{mentor_id}", produces = "application/json")
    public ResponseEntity<Object> getMentorById(@PathVariable String mentor_id) {
        Optional<Mentor> mentor = mentorRepo.findById(mentor_id);
        if (mentor.isPresent()) {
            response.setData(mentor.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/mentors/{mentor_id}")
    public ResponseEntity<Object> deleteMentor(@PathVariable String mentor_id) {
        if (mentorRepo.existsById(mentor_id)) {
            mentorRepo.deleteById(mentor_id);
            response.setErrorCode(null);
            response.setData("Mentor with ID " + mentor_id + " deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

