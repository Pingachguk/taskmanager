package com.ic.taskmanager.interfaces;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import javax.mail.internet.MimeBodyPart;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public void init();

    public void createDirecotry(String directoryName);

    public void save(MultipartFile file);

    public void save(MimeBodyPart file);

    public void save(MultipartFile file, String addPath) throws IOException;

    public void save(MimeBodyPart file, String addPath) throws IOException;

    public Path load(String filename);

    public Stream<Path> loadAll();

    public void deleteAll();
}
