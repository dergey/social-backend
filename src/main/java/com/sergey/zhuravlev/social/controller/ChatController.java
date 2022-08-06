package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.ChatDto;
import com.sergey.zhuravlev.social.dto.ChatPreviewDto;
import com.sergey.zhuravlev.social.dto.CreateChatDto;
import com.sergey.zhuravlev.social.entity.Chat;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.mapper.ChatMapper;
import com.sergey.zhuravlev.social.service.ChatService;
import com.sergey.zhuravlev.social.service.MessageService;
import com.sergey.zhuravlev.social.service.ProfileService;
import com.sergey.zhuravlev.social.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Chat endpoints")
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;
    private final MessageService messageService;
    private final ProfileService profileService;

    private final ChatMapper chatMapper;

    @Operation(description = "Gets the chats of the current user")
    @GetMapping
    public Page<ChatPreviewDto> getCurrentUserChats(@ParameterObject Pageable pageable) {
        User user = userService.getCurrentUser();
        Page<Chat> chat = chatService.getChats(user, pageable);
        return chat.map(chatMapper::chatToChatPreviewDto);
    }

    @Operation(description = "Creates or gets a chat with the specified user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ChatDto getOrCreateChat(@Valid @RequestBody CreateChatDto createChatDto) {
        User currentUser = userService.getCurrentUser();
        Profile targetProfile = profileService.getProfile(createChatDto.getTargetUsername());
        Chat chat = chatService.getOrCreateChat(currentUser, targetProfile);
        return chatMapper.chatAndLastMessagesToChatDto(chat, messageService.getChatLastMessages(chat, 5));
    }

    @Operation(description = "Marks chat messages as read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{id}/read")
    public void updateReadStatus(@PathVariable Long id) {
        Chat chat = chatService.getChat(userService.getCurrentUser(), id);
        chatService.updateReadStatus(chat);
    }

    @Operation(description = "Unblocks the chat for writing new messages")
    @PostMapping("/{id}/unblock")
    public ChatDto unblockChat(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }
    
    @Operation(description = "Blocks the chat for writing new messages")
    @PostMapping("/{id}/block")
    public ChatDto blockChat(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }

}
