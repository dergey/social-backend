package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
