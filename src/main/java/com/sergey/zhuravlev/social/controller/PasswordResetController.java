package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.ContinuationDto;
import com.sergey.zhuravlev.social.dto.confirmation.ManualCodeConfirmationDto;
import com.sergey.zhuravlev.social.dto.reset.CompletePasswordResetDto;
import com.sergey.zhuravlev.social.dto.reset.PasswordResetStatusDto;
import com.sergey.zhuravlev.social.dto.reset.StartPasswordResetDto;
import com.sergey.zhuravlev.social.entity.PasswordReset;
import com.sergey.zhuravlev.social.service.PasswordResetFlowService;
import com.sergey.zhuravlev.social.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Tag(name = "Password reset endpoints")
@RestController
@RequestMapping("/api/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetFlowService passwordResetFlowService;

    @Operation(description = "Initializes the password reset flow")
    @PostMapping
    public PasswordResetStatusDto startPasswordReset(@Valid @RequestBody StartPasswordResetDto dto) {
        PasswordReset passwordReset = passwordResetFlowService.startPasswordReset(dto.getPhoneOrEmail());
        return new PasswordResetStatusDto(passwordReset.getStatus(), passwordReset.getContinuationCode());
    }

    @Operation(description = "Confirms email or phone by code")
    @PostMapping("/confirm/code")
    public PasswordResetStatusDto confirmByCode(@Valid @RequestBody ManualCodeConfirmationDto dto) {
        PasswordReset passwordReset = passwordResetFlowService.confirmPasswordResetByManualCode(dto.getContinuationCode(), dto.getManualCode());
        return new PasswordResetStatusDto(passwordReset.getStatus(), passwordReset.getContinuationCode());
    }

    @Operation(description = "Confirms email by link (long code)")
    @PostMapping("/confirm/link")
    public PasswordResetStatusDto confirmByLink(@Valid @NotBlank @RequestParam @Schema(example = "R0dj6wT9Uu8fuYXk") String linkCode) {
        PasswordReset passwordReset = passwordResetFlowService.confirmPasswordResetByLinkCode(linkCode);
        return new PasswordResetStatusDto(passwordReset.getStatus(), passwordReset.getContinuationCode());
    }

    @Operation(description = "Re-sends confirmation message")
    @PostMapping("/resend")
    public PasswordResetStatusDto resendConfirmation(@Valid @RequestBody ContinuationDto continuationDto) {
        PasswordReset passwordReset = passwordResetFlowService.resendPasswordResetConfirmation(continuationDto.getContinuationCode());
        return new PasswordResetStatusDto(passwordReset.getStatus(), passwordReset.getContinuationCode());
    }

    @Operation(description = "Completes the password reset flow, sets the new password for the user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/complete")
    public void completePasswordReset(@Valid @RequestBody CompletePasswordResetDto dto) {
        passwordResetFlowService.completePasswordReset(dto.getContinuationCode(), dto.getPassword());
    }

}
