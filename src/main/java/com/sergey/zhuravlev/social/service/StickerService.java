package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.entity.Image;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.Sticker;
import com.sergey.zhuravlev.social.entity.StickerPack;
import com.sergey.zhuravlev.social.exception.ObjectNotFoundException;
import com.sergey.zhuravlev.social.repository.StickerPackRepository;
import com.sergey.zhuravlev.social.repository.StickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StickerService {

    public final StickerRepository stickerRepository;
    public final StickerPackRepository stickerPackRepository;

    @Transactional(readOnly = true)
    public StickerPack getStickerPackBySticker(Long stickerId) {
        return stickerPackRepository.findByStickersId(stickerId)
                .orElseThrow(() -> new ObjectNotFoundException(Objects.toString(stickerId), StickerPack.class.getSimpleName()));
    }

    @Transactional(readOnly = true)
    public StickerPack getStickerPack(Long stickerPackId) {
        return stickerPackRepository.findById(stickerPackId)
                .orElseThrow(() -> new ObjectNotFoundException(Objects.toString(stickerPackId), StickerPack.class.getSimpleName()));
    }

    @Transactional
    public StickerPack createStickerPack(Profile profile, String title) {
        return stickerPackRepository.save(new StickerPack(null, profile, title, new ArrayList<>(),
                LocalDateTime.now(), LocalDateTime.now()));
    }

    @Transactional
    public void deleteStickerPack(Profile profile, Long stickerPackId) {
        StickerPack stickerPack = stickerPackRepository.findById(stickerPackId)
                .orElseThrow(() -> new ObjectNotFoundException(Objects.toString(stickerPackId), StickerPack.class.getSimpleName()));
        checkModifyRight(stickerPack, profile);
        stickerPackRepository.delete(stickerPack);
    }

    @Transactional
    public Sticker addStickerPackSticker(Profile profile, Long stickerPackId, Image image, String emoji) {
        StickerPack stickerPack = stickerPackRepository.findById(stickerPackId)
                .orElseThrow(() -> new ObjectNotFoundException(Objects.toString(stickerPackId), StickerPack.class.getSimpleName()));
        checkModifyRight(stickerPack, profile);
        return stickerRepository.save(new Sticker(null, emoji, image, stickerPack));
    }

    @Transactional
    public void deleteStickerPackSticker(Profile profile, Long stickerPackId, Long stickerId) {
        StickerPack stickerPack = stickerPackRepository.findById(stickerPackId)
                .orElseThrow(() -> new ObjectNotFoundException(Objects.toString(stickerPackId), StickerPack.class.getSimpleName()));
        checkModifyRight(stickerPack, profile);
        stickerRepository.deleteByStickerPackIdAndId(stickerPackId, stickerId);
    }

    private void checkModifyRight(StickerPack stickerPack, Profile authorizedUser) {
        if (stickerPack.getAuthor().equals(authorizedUser)) {
            throw new AccessDeniedException("You do not have enough permissions to modify this StickerPack");
        }
    }

}
