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

import com.example.API.Repository.AttendanceRepository;
import com.example.API.Response.ResponseBean;
import com.example.API.entity.Attendance;

@RestController
public class AttendanceController {

    @Autowired
    public AttendanceRepository attendanceRepo;

    @Autowired
    public ResponseBean response;

    @GetMapping(path = "/attendances", produces = "application/json")
    public ResponseEntity<Object> getAttendances() {
        List<Attendance> attendanceList = attendanceRepo.findAll();
        response.setData(attendanceList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/attendances/{uid}", produces = "application/json")
public ResponseEntity<Object> getAttendanceByUid(@PathVariable String uid) {
    try {
        Optional<Attendance> attendance = attendanceRepo.findById(uid);
        if (attendance.isPresent()) {
            response.setErrorCode(null);  // Resetting error code for successful response
            response.setData(attendance.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("Attendance record not found");
            response.setData(null);  // Clearing data in case of error
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        // Catching any unexpected exceptions
        response.setErrorCode("ERR500");  // General error code for internal server error
        response.setData("An error occurred while retrieving attendance: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@DeleteMapping(path = "/attendances/{uid}")
public ResponseEntity<Object> deleteAttendance(@PathVariable String uid) {
    try {
        if (attendanceRepo.existsById(uid)) {
            attendanceRepo.deleteById(uid);
            response.setErrorCode(null);  // Resetting error code for successful response
            response.setData("Attendance record with UID " + uid + " deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("Attendance record not found");
            response.setData(null);  // Clearing data in case of error
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        // Catching any unexpected exceptions
        response.setErrorCode("ERR500");  // General error code for internal server error
        response.setData("An error occurred while deleting attendance: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


}
