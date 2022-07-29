package com.ic.taskmanager.service;

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
        System.out.println("Sche1122dule1");

        for (Job j : jobList) {
            j.work();
        }
    }
}
