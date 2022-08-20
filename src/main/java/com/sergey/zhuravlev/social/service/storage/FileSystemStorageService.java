package com.sergey.zhuravlev.social.service.storage;

import com.sergey.zhuravlev.social.config.properties.StorageProperties;
import com.sergey.zhuravlev.social.service.storage.exception.StorageException;
import com.sergey.zhuravlev.social.service.storage.exception.StorageFileNotFoundException;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String destinationFilename = generateNewFilename();
            Path destinationFile = this.rootLocation.resolve(Paths.get(destinationFilename)).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return destinationFilename;
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public String store(byte[] bytes) {
        try {
            if (bytes == null || bytes.length == 0) {
                throw new StorageException("Failed to store empty file.");
            }
            String destinationFilename = generateNewFilename();
            Path destinationFile = this.rootLocation.resolve(Paths.get(destinationFilename)).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return destinationFilename;
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    @SneakyThrows
    public byte[] load(String id) {
        Resource resource = loadAsResource(id);
        final int bufferLength = 4 * 0x400; // 4KB
        byte[] buf = new byte[bufferLength];
        int readLength;

        InputStream inputStream = resource.getInputStream();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            while ((readLength = inputStream.read(buf, 0, bufferLength)) != -1) {
                outputStream.write(buf, 0, readLength);
            }
            inputStream.close();
            return outputStream.toByteArray();
        }
    }

    @Override
    public InputStream loadAsInputStream(String id) throws IOException {
        Resource resource = loadAsResource(id);
        return resource.getInputStream();
    }

    @Override
    public Resource loadAsResource(String id) {
        try {
            Path file = rootLocation.resolve(id);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + id);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + id, e);
        }
    }

    @Override
    public boolean contain(String id) {
        Path path = rootLocation.resolve(id);
        return Files.exists(path) && Files.isReadable(path);
    }

    @Override
    public void clear(String id) {
        Path path = rootLocation.resolve(id);
        if (!Files.exists(path)) {
            throw new StorageFileNotFoundException("Could not read file: " + id);
        }
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageFileNotFoundException("Could not read file: " + id, e);
        }
    }

    @Override
    public void clearAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public int getStoragePriority() {
        return 0;
    }

    private String generateNewFilename() {
        return UUID.randomUUID().toString();
    }
}