package com.ic.taskmanager.services;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.ic.taskmanager.interfaces.Job;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class JobService {

    private static List<Job> jobList = new ArrayList<>();

    @Scheduled(fixedRate = 5000)
    private void scheduler() {
        System.out.println("Schedule1");

        for (Job j : jobList) {
            j.work();
        }
    }
}