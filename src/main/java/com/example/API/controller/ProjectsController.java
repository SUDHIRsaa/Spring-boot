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

import com.example.API.Repository.ProjectsRepository;
import com.example.API.Response.ResponseBean;
import com.example.API.entity.Projects;

@RestController
public class ProjectsController {

    @Autowired
    public ProjectsRepository projectsRepo;

    @Autowired
    public ResponseBean response;

    @GetMapping(path = "/projects", produces = "application/json")
    public ResponseEntity<Object> getProjects() {
        List<Projects> projectList = projectsRepo.findAll();
        response.setData(projectList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/projects/{batch_no}", produces = "application/json")
    public ResponseEntity<Object> getProjectByBatchNo(@PathVariable int batch_no) {
        Optional<Projects> project = projectsRepo.findById(batch_no);
        if (project.isPresent()) {
            response.setData(project.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode( "Project not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/projects/{batch_no}")
    public ResponseEntity<Object> deleteProject(@PathVariable int batch_no) {
        if (projectsRepo.existsById(batch_no)) {
            projectsRepo.deleteById(batch_no);
            response.setErrorCode(null);
            response.setData("Project with Batch No " + batch_no + " deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode( "Project not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

