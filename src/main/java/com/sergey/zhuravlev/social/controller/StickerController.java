package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.sticker.CreateStickerPackDto;
import com.sergey.zhuravlev.social.dto.sticker.StickerDto;
import com.sergey.zhuravlev.social.dto.sticker.StickerPackDto;
import com.sergey.zhuravlev.social.entity.*;
import com.sergey.zhuravlev.social.mapper.StickerMapper;
import com.sergey.zhuravlev.social.service.ImageService;
import com.sergey.zhuravlev.social.service.ProfileService;
import com.sergey.zhuravlev.social.service.StickerService;
import com.sergey.zhuravlev.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sticker")
@RequiredArgsConstructor
public class StickerController {

    private final UserService userService;
    private final ImageService imageService;
    private final ProfileService profileService;
    private final StickerService stickerService;

    private final StickerMapper stickerMapper;

    @GetMapping
    public StickerPackDto getStickerPackBySticker(@RequestParam(name = "stickerId") Long stickerId) {
        StickerPack stickerPack = stickerService.getStickerPackBySticker(stickerId);
        return stickerMapper.stickerPackToStickerPackDto(stickerPack);
    }

    @GetMapping("/{stickerPackId}")
    public StickerPackDto getStickerPack(@PathVariable Long stickerPackId) {
        StickerPack stickerPack = stickerService.getStickerPack(stickerPackId);
        return stickerMapper.stickerPackToStickerPackDto(stickerPack);
    }

    @PostMapping
    public StickerPackDto createStickerPack(@RequestBody @Valid CreateStickerPackDto dto) {
        User user = userService.getCurrentUser();
        Profile profile = profileService.getProfile(user);
        StickerPack stickerPack = stickerService.createStickerPack(profile, dto.getTitle());
        return stickerMapper.stickerPackToStickerPackDto(stickerPack);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{stickerPackId}")
    public void deleteStrikerPack(@PathVariable Long stickerPackId) {
        User user = userService.getCurrentUser();
        Profile profile = profileService.getProfile(user);
        stickerService.deleteStickerPack(profile, stickerPackId);
    }

    @GetMapping("/{stickerPackId}/sticker")
    public Collection<StickerDto> getStickerPackStickers(@PathVariable Long stickerPackId) {
        return stickerService.getStickerPack(stickerPackId)
                .getStickers().stream()
                .map(stickerMapper::stickerToStickerDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{stickerPackId}/sticker")
    public StickerDto addStickerPackSticker(@PathVariable Long stickerPackId,
                                            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        User user = userService.getCurrentUser();
        Profile profile = profileService.getProfile(user);
        Image stickerImage = imageService.createImage(user, multipartFile);
        Sticker sticker = stickerService.addStickerPackSticker(profile, stickerPackId, stickerImage, null);
        return stickerMapper.stickerToStickerDto(sticker);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{stickerPackId}/sticker/{stickerId}")
    public void deleteStickerPackSticker(@PathVariable Long stickerPackId, @PathVariable Long stickerId) {
        User user = userService.getCurrentUser();
        Profile profile = profileService.getProfile(user);
        stickerService.deleteStickerPackSticker(profile, stickerPackId, stickerId);
    }

}
