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

import com.example.API.Repository.BatchRepository;
import com.example.API.Response.ResponseBean;
import com.example.API.entity.Batch;

@RestController
public class BatchController {

    @Autowired
    public BatchRepository batchRepo;

    @Autowired
    public ResponseBean response;

    @GetMapping(path = "/batches", produces = "application/json")
    public ResponseEntity<Object> getBatches() {
        List<Batch> batchList = batchRepo.findAll();
        response.setData(batchList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/batches/{batch_no}", produces = "application/json")
    public ResponseEntity<Object> getBatchByNo(@PathVariable int batch_no) {
        Optional<Batch> batch = batchRepo.findById(batch_no);
        if (batch.isPresent()) {
            response.setData(batch.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode( "Batch not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/batches/{batch_no}")
    public ResponseEntity<Object> deleteBatch(@PathVariable int batch_no) {
        if (batchRepo.existsById(batch_no)) {
            batchRepo.deleteById(batch_no);
            response.setErrorCode(null);
            response.setData("Batch with number " + batch_no + " deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode( "Batch not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
