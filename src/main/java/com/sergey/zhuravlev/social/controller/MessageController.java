package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.message.*;
import com.sergey.zhuravlev.social.entity.Chat;
import com.sergey.zhuravlev.social.entity.Image;
import com.sergey.zhuravlev.social.entity.Message;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.mapper.MessageMapper;
import com.sergey.zhuravlev.social.service.ChatService;
import com.sergey.zhuravlev.social.service.ImageService;
import com.sergey.zhuravlev.social.service.MessageService;
import com.sergey.zhuravlev.social.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.ZoneId;

@Tag(name = "Message endpoints")
@RestController
@RequestMapping("/api/chat/{chatId}/message")
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;
    private final UserService userService;
    private final ImageService imageService;
    private final MessageService messageService;

    @GetMapping
    public Page<MessageDto> getChatMessages(@PathVariable Long chatId, @ParameterObject Pageable pageable) {
        Chat chat = chatService.getChat(userService.getCurrentUser(), chatId);
        return messageService.getChatMessages(chat, pageable).map(MessageMapper::messageToMessageDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MessageDto createMessage(@PathVariable Long chatId, @Valid @RequestBody CreateMessageDto dto) {
        User currentUser = userService.getCurrentUser();
        Chat chat = chatService.getChat(currentUser, chatId);
        if (dto instanceof CreateTextMessageDto) {
            return MessageMapper.messageToMessageDto(messageService.createTextMessage(
                    chat,
                    ((CreateTextMessageDto) dto).getText()));
        } else if (dto instanceof CreateStickerMessageDto) {
            throw new UnsupportedOperationException();
        } else {
            throw new IllegalArgumentException("Unknown class type of createMessageDto");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/image")
    public MessageDto createImageMessage(@PathVariable Long chatId, @RequestParam("image") MultipartFile multipartFile)
            throws IOException {
        User currentUser = userService.getCurrentUser();
        Chat chat = chatService.getChat(currentUser, chatId);
        Image image = imageService.createImage(currentUser, multipartFile);
        Message message = messageService.createImageMessage(chat, image);
        return MessageMapper.messageToMessageDto(message);
    }

    @PutMapping("/{messageId}")
    public MessageDto updateTextMessage(@PathVariable Long chatId, @PathVariable Long messageId,
                                        @Valid @RequestBody UpdateTextMessageDto dto) {
        User currentUser = userService.getCurrentUser();
        Chat chat = chatService.getChat(currentUser, chatId);
        Message message = messageService.getMessage(chat, messageId);
        message = messageService.updateTextMessage(message, dto.getText());
        return MessageMapper.messageToMessageDto(message);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable Long chatId, @PathVariable Long messageId) {
        Chat chat = chatService.getChat(userService.getCurrentUser(), chatId);
        messageService.deleteMessage(chat, messageId);
    }

    // Getting messages image

    @GetMapping("/{messageId}/image")
    public ResponseEntity<byte[]> getMessageImage(@PathVariable Long chatId, @PathVariable Long messageId,
                                                  WebRequest request) {
        Chat chat = chatService.getChat(userService.getCurrentUser(), chatId);
        Message message = messageService.getMessage(chat, messageId);

        // Check for data changes
        ZoneId zoneId = ZoneId.of("GMT");
        long lastModifiedTimestamp = message.getUpdateAt().atZone(zoneId).toInstant().toEpochMilli();
        if (request.checkNotModified(lastModifiedTimestamp)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        Image image = imageService.fetchImage(message.getImage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf(image.getMimeType()))
                .body(image.getData());
    }

    @GetMapping("/{messageId}/image_preview")
    public ResponseEntity<byte[]> getMessageImagePreview(@PathVariable Long chatId, @PathVariable Long messageId,
                                                         WebRequest request) {
        Chat chat = chatService.getChat(userService.getCurrentUser(), chatId);
        Message message = messageService.getMessage(chat, messageId);

        // Check for data changes
        ZoneId zoneId = ZoneId.of("GMT");
        long lastModifiedTimestamp = message.getUpdateAt().atZone(zoneId).toInstant().toEpochMilli();
        if (request.checkNotModified(lastModifiedTimestamp)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        Image image = imageService.fetchPreviewImage(message.getImage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image.getPreview());
    }

}
