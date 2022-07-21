package com.ic.taskmanager.controller;

import com.ic.taskmanager.model.JobResponse;
import com.ic.taskmanager.service.JobService;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/job")
public class JobController {
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping(path = "/get-list")
    public ResponseEntity<JobResponse> getAllJobs() {
        return null;
    }

    @GetMapping(path = "/get-by-id/{id}")
    public void getJobById(@PathVariable int id) {
    }

    @PostMapping(path = "/add")
    public void addJob(RequestEntity<?> request) {
    }

    @PutMapping(path = "/edit/{id}")
    public void editJob(RequestEntity<?> request, @PathVariable int id) {
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteJob(@PathVariable int id) {
    }
}
