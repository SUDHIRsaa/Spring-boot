package com.example.API.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @GetMapping(path = "/attendances", produces = "application/json")
    public ResponseEntity<Object> getAttendances() {
        logger.info("Fetching all attendances");
        List<Attendance> attendanceList = attendanceRepo.findAll();
        response.setData(attendanceList);
        logger.debug("Attendance data: " + attendanceList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/attendances/{uid}", produces = "application/json")
public ResponseEntity<Object> getAttendanceByUid(@PathVariable String uid) {
    logger.info("Fetching attendance for UID: " + uid);
    try {
        Optional<Attendance> attendance = attendanceRepo.findById(uid);
        if (attendance.isPresent()) {
            response.setErrorCode(null);  
            response.setData(attendance.get());
            logger.debug("Attendance data for UID " + uid + ": " + attendance.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("Attendance record not found");
           
            response.setData(null);  
            logger.warn("Attendance record not found for UID: " + uid);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
       
        logger.error("Error fetching attendance for UID: " + uid + ": " + e.getMessage());
        response.setErrorCode("ERR500");  
        response.setData("An error occurred while retrieving attendance: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@DeleteMapping(path = "/attendances/{uid}")
public ResponseEntity<Object> deleteAttendance(@PathVariable String uid) {
    logger.info("Deleting attendance for UID: " + uid);
    try {
        if (attendanceRepo.existsById(uid)) {
            attendanceRepo.deleteById(uid);
            response.setErrorCode(null);  
           
            response.setData("Attendance record with UID " + uid + " deleted successfully");
            logger.info("Deleted attendance for UID: " + uid);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("Attendance record not found");
            response.setData(null);  
            logger.warn("Attendance record not found for UID: " + uid);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        logger.error("Error deleting attendance for UID: " + uid + ": " + e.getMessage());
        response.setErrorCode("ERR500"); 
        response.setData("An error occurred while deleting attendance: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


}
