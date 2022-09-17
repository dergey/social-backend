package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.configuration.message.ErrorMessageResolver;
import com.sergey.zhuravlev.social.dto.ErrorDto;
import com.sergey.zhuravlev.social.dto.FieldsErrorDto;
import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.exception.*;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorController {

    private final ErrorMessageResolver errors;

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class })
    public ErrorDto handleException(Exception ex) {
        log.warn("Status 500: ", ex);
        return new ErrorDto(ErrorCode.UNKNOWN_ERROR, ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ SocialServiceException.class })
    public ErrorDto handleSocialServiceException(SocialServiceException ex) {
        log.debug("Status 500: ", ex);
        return new ErrorDto(ex.getCode(), errors.getMessage(ex, Locale.getDefault()));
    }

    @Hidden
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ AuthenticationException.class })
    public ErrorDto handleAuthenticationException(AuthenticationException ex) {
        return new ErrorDto(ErrorCode.UNAUTHORIZED, ex.getMessage());
    }

    @Hidden
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler({ EntityNotFoundException.class })
    public ErrorDto handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ErrorDto(ErrorCode.NOT_FOUND, ex.getMessage());
    }

    @Hidden
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler({ ObjectNotFoundException.class })
    public ErrorDto handleObjectNotFoundException(ObjectNotFoundException ex) {
        return new ErrorDto(ErrorCode.NOT_FOUND, ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public final ErrorDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Class<?> parameterType = ex.getParameter().getParameterType();
        String additionalEnumHint = "";
        if (parameterType.isEnum()) {
            additionalEnumHint = ". Only allowed values of " + Arrays.toString(parameterType.getEnumConstants());
        }
        return new ErrorDto(ErrorCode.ARGUMENT_TYPE_MISMATCH, String.format("Method parameter '%s' contain illegal value", ex.getName())
                + additionalEnumHint);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ FieldAlreadyExistsException.class })
    public final FieldsErrorDto handleFieldAlreadyExistsException(FieldAlreadyExistsException ex) {
        return new FieldsErrorDto(ErrorCode.FIELD_ERROR, Collections.singletonList(
                new FieldsErrorDto.FieldError(ex.getAttribute(), ex.getMessage())
        ));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public final FieldsErrorDto handleFieldsException(MethodArgumentNotValidException ex) {
        return new FieldsErrorDto(ErrorCode.NOT_VALID, ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new FieldsErrorDto.FieldError(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ AlreadyExistsException.class })
    public final ErrorDto handleAlreadyExistsException(AlreadyExistsException ex) {
        return new ErrorDto(ErrorCode.ALREADY_EXIST, ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ SocialServiceFieldException.class })
    public final FieldsErrorDto handleSocialServiceFieldException(SocialServiceFieldException ex) {
        return new FieldsErrorDto(ErrorCode.NOT_VALID,
                Collections.singletonList(new FieldsErrorDto.FieldError(ex.getField(), ex.getCode().toString()))
        );
    }

    // Note: remove method after development

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ UnsupportedOperationException.class })
    public final ErrorDto handleUnsupportedOperationException(UnsupportedOperationException ex) {
        return new ErrorDto(ErrorCode.NOT_IMPLEMENTED, "This method contains unimplemented functionality. " +
                "Contact the developer");
    }

}
