package com.ic.taskmanager.services;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
    @Autowired
    private ServletContext context;
    private String uploadDir = "/uploads";

    public void upload(MultipartFile file) throws IllegalStateException, IOException {
        String filePath = this.context.getRealPath("/");
        file.transferTo(new File(filePath));
    }

    public void remove(String pathToFile) {}
}
