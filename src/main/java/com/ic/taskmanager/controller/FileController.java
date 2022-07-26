package com.ic.taskmanager.controller;

import com.ic.taskmanager.interfaces.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(path = "{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        FileSystemResource file = new FileSystemResource(this.fileService.load(filename));
        HttpHeaders headers = new HttpHeaders();

        MediaType mediaType = MediaTypeFactory.getMediaType(file).orElse(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentType(mediaType);

        ContentDisposition disposition = ContentDisposition.inline().filename(file.getFilename()).build();
        headers.setContentDisposition(disposition);

        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }
}
