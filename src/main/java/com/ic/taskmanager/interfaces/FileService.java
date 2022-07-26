package com.ic.taskmanager.interfaces;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    public void init();
    public void save(MultipartFile file);
    public void save(MultipartFile file, String addPath) throws IOException;
    public Resource load(String filename);
    public Stream<Path> loadAll();
    public void deleteAll();
}
