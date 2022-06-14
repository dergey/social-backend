package com.sergey.zhuravlev.social.service;


import com.sergey.zhuravlev.social.entity.Image;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.enums.ImageSize;
import com.sergey.zhuravlev.social.repository.ImageRepository;
import com.sergey.zhuravlev.social.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageReader;
import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final int PREVIEW_HEIGHT = 512;
    private static final int PREVIEW_WIDTH = 512;

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public Image getImage(User user, Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Image not found by id %s for user %s", imageId, user.getEmail())));
    }

    @Transactional(readOnly = true)
    public Image fetchImage(Image image) {
        image = imageRepository.getById(image.getId());
        Hibernate.initialize(image.getData());
        return image;
    }

    @Transactional(readOnly = true)
    public Image fetchPreviewImage(Image image) {
        image = imageRepository.getById(image.getId());
        Hibernate.initialize(image.getPreview());
        return image;
    }

    @Transactional(propagation = Propagation.NEVER)
    public Image createImage(User user, MultipartFile multipartFile) throws IOException {
        ImageReader imageReader = ImageUtils.getImageReader(multipartFile.getInputStream());
        BufferedImage bufferedImage = imageReader.read(0);
        String mimeType = imageReader.getOriginatingProvider().getMIMETypes()[0];
        BufferedImage previewBufferedImage = Scalr.resize(bufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, PREVIEW_WIDTH, PREVIEW_HEIGHT);
        byte[] imageData = ImageUtils.getBufferImageBytes(bufferedImage);
        byte[] previewData = ImageUtils.getBufferImageBytes(previewBufferedImage);

        return imageRepository.save(new Image(null,
                user,
                previewData,
                mimeType,
                bufferedImage.getHeight(),
                bufferedImage.getWidth(),
                DataSize.ofBytes(imageData.length),
                imageData,
                LocalDateTime.now()
        ));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Image generateAvatarImage(User user, String firstName, String secondName) {
        BufferedImage bufferedImage = ImageUtils.generateDefaultAvatar("" + firstName.charAt(0) + secondName.charAt(0),
                ImageSize._256x256);
        BufferedImage previewBufferedImage = Scalr.resize(bufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC,
                ImageSize._50x50.getWidth(), ImageSize._50x50.getHeight());
        byte[] imageData = ImageUtils.getBufferImageBytes(bufferedImage);
        byte[] previewData = ImageUtils.getBufferImageBytes(previewBufferedImage);

        return imageRepository.save(new Image(null,
                user,
                previewData,
                MediaType.IMAGE_JPEG_VALUE,
                bufferedImage.getHeight(),
                bufferedImage.getWidth(),
                DataSize.ofBytes(imageData.length),
                imageData,
                LocalDateTime.now()
        ));
    }

}
