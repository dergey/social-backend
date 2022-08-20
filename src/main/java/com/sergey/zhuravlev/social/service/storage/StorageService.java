package com.sergey.zhuravlev.social.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface StorageService {

    String store(MultipartFile file);

    String store(byte[] bytes);

    byte[] load(String id);

    InputStream loadAsInputStream(String id) throws IOException;

    Resource loadAsResource(String id);

    boolean contain(String id);

    void clear(String id);

    void clearAll();

    int getStoragePriority();

}
