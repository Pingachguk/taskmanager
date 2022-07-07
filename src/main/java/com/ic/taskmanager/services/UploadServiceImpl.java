package com.ic.taskmanager.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ic.taskmanager.interfaces.UploadService;

@Service
public class UploadServiceImpl implements UploadService {
    private final Path root = Paths.get("uploads");

    @PostConstruct
    @Override
    public void init() {
        if (!Files.exists(this.root)) {
            try {
                Files.createDirectory(this.root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root
                    .resolve(UUID.randomUUID().toString() + "."
                            + FilenameUtils.getExtension(file.getOriginalFilename())));
        } catch (Exception e) {
            throw new RuntimeException("[-] Невозможно сохранить файл. Причина: " + e.getMessage());
        }
    }

    @Override
    public void save(MultipartFile file, String addPath) throws IOException {
        Path subpath = Paths.get(this.root.toString() + "/" + addPath);

        if (!Files.exists(subpath)) {
            Files.createDirectory(subpath);
        }

        try {
            Files.copy(file.getInputStream(), subpath
                    .resolve(UUID.randomUUID().toString() + "."
                            + FilenameUtils.getExtension(file.getOriginalFilename())));
        } catch (Exception e) {
            throw new RuntimeException("[-] Невозможно сохранить файл. Причина: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        // TODO: Загрузить файл с названием filename
        return null;
    }

    @Override
    public Stream<Path> loadAll() {
        // TODO: Загрузить все файлы
        return null;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }
}
