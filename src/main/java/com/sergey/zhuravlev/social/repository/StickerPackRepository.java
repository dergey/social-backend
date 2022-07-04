package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.StickerPack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StickerPackRepository extends JpaRepository<StickerPack, Long> {

    Optional<StickerPack> findByStickersId(Long stickerId);

}
