package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
    void deleteByStickerPackIdAndId(Long stickerPackId, Long stickerId);
}
