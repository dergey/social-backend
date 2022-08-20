package com.sergey.zhuravlev.social.service;


import com.sergey.zhuravlev.social.entity.Image;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.enums.ImageSize;
import com.sergey.zhuravlev.social.repository.ImageRepository;
import com.sergey.zhuravlev.social.service.storage.StorageService;
import com.sergey.zhuravlev.social.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final StorageService storageService;

    public Resource fetchImageResource(Image image) {
        return storageService.loadAsResource(image.getStorageId());
    }

    @Cacheable(cacheNames = "preview-cache", key = "T(java.util.Objects).hash(#image.id, #width, #height)")
    public Resource fetchPreviewImageResource(Image image, Integer width, Integer height) {
        try (InputStream inputStream = storageService.loadAsInputStream(image.getStorageId())) {
            ImageReader imageReader = ImageUtils.getImageReader(inputStream);
            BufferedImage bufferedImage = imageReader.read(0);
            BufferedImage previewBufferedImage = Scalr.resize(bufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC,
                    width, height);
            byte[] previewData = ImageUtils.getBufferImageBytes(previewBufferedImage);
            return new ByteArrayResource(previewData);
        } catch (IOException e) {
            throw new RuntimeException("Image can't be loaded", e);
        }
    }

    public Image createImage(User user, MultipartFile multipartFile) throws IOException {
        ImageReader imageReader = ImageUtils.getImageReader(multipartFile.getInputStream());
        BufferedImage bufferedImage = imageReader.read(0);
        String mimeType = imageReader.getOriginatingProvider().getMIMETypes()[0];
        byte[] imageData = ImageUtils.getBufferImageBytes(bufferedImage);

        return imageRepository.save(new Image(null,
                user,
                mimeType,
                bufferedImage.getHeight(),
                bufferedImage.getWidth(),
                DataSize.ofBytes(imageData.length),
                storageService.store(imageData),
                LocalDateTime.now()
        ));
    }

    public Image generateAvatarImage(User user, String firstName, String secondName) {
        BufferedImage bufferedImage = ImageUtils.generateDefaultAvatar("" + firstName.charAt(0) + secondName.charAt(0),
                ImageSize._256x256);
        byte[] imageData = ImageUtils.getBufferImageBytes(bufferedImage);

        return imageRepository.save(new Image(null,
                user,
                MediaType.IMAGE_JPEG_VALUE,
                bufferedImage.getHeight(),
                bufferedImage.getWidth(),
                DataSize.ofBytes(imageData.length),
                storageService.store(imageData),
                LocalDateTime.now()
        ));
    }

}
