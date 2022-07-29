package com.ic.taskmanager.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ic.taskmanager.interfaces.FileService;

@Service
public class FileServiceImpl implements FileService {
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
            InputStream in = file.getInputStream();

            // Путь до файла + его название
            Path endpoint = this.root.resolve(DigestUtils.sha256Hex(in) + "."
                    + FilenameUtils.getExtension(file.getOriginalFilename()));
            if (!Files.exists(endpoint)) {
                Files.copy(in, endpoint);
            }
        } catch (Exception e) {
            throw new RuntimeException("[-] Невозможно сохранить файл. Причина: " + e.getMessage());
        }
    }

    @Override
    public void save(MimeBodyPart file) {
        try {
            String filename = MimeUtility.decodeText(file.getFileName());
            InputStream in = file.getInputStream();

            // Путь до файла + его название
            Path endpoint = this.root.resolve(DigestUtils.sha256Hex(in) + "."
                    + FilenameUtils.getExtension(filename));
            if (!Files.exists(endpoint)) {
                Files.copy(in, endpoint);
            }
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
            InputStream in = file.getInputStream();

            // Путь до файла + его название
            Path endpoint = subpath.resolve(DigestUtils.sha256Hex(in) + "."
                    + FilenameUtils.getExtension(file.getOriginalFilename()));
            if (!Files.exists(endpoint)) {
                Files.copy(in, endpoint);
            }
        } catch (Exception e) {
            throw new RuntimeException("[-] Невозможно сохранить файл. Причина: " + e.getMessage());
        }
    }

    @Override
    public void save(MimeBodyPart file, String addPath) throws IOException {
        Path subpath = Paths.get(this.root.toString() + "/" + addPath);

        if (!Files.exists(subpath)) {
            Files.createDirectories(subpath);
        }

        try {
            String filename = MimeUtility.decodeText(file.getFileName());
            InputStream in = file.getInputStream();

            // Путь до файла + его название
            Path endpoint = subpath.resolve(DigestUtils.sha256Hex(in) + "." + FilenameUtils.getExtension(filename));
            if (!Files.exists(endpoint)) {
                Files.copy(in, endpoint);
            }
        } catch (Exception e) {
            throw new RuntimeException("[-] Невозможно сохранить файл. Причина: " + e.getMessage());
        }
    }

    @Override
    public Path load(String filename) {
        return Paths.get(this.root + "/" + filename);
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

    @Override
    public void createDirecotry(String directoryName) {
        new File(this.root + "/" + directoryName).mkdirs();
    }
}
