package com.ic.taskmanager.controller;

import com.ic.taskmanager.interfaces.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(path = "{filename}")
    public FileSystemResource getFile(@PathVariable("fileName") String fileName) {
        return new FileSystemResource(this.fileService.load(fileName));
    }
}
