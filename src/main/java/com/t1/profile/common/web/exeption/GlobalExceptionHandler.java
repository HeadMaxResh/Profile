package com.t1.profile.common.web.exeption;

import com.t1.profile.common.web.response.ErrorResponse;
import com.t1.profile.profession.exception.ProfessionNotFoundException;
import com.t1.profile.skill.hard.exception.HardSkillNotFoundException;
import com.t1.profile.skill.hard.exception.UserHardSkillAssociationAlreadyExistException;
import com.t1.profile.skill.hard.exception.UserHardSkillAssociationNotFoundException;
import com.t1.profile.skill.hard.exception.UserHardSkillNotFoundException;
import com.t1.profile.skill.soft.exception.CategorySoftSkillNotFoundException;
import com.t1.profile.skill.soft.exception.SoftSkillNotFoundException;
import com.t1.profile.skill.soft.exception.UserSoftSkillNotFoundException;
import com.t1.profile.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @SneakyThrows
    ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        log.error("Exception occurred: ", ex);
        log.info("request: {}", request);

        return ResponseEntity.status(500).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @SneakyThrows
    ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException ex,
            WebRequest request
    ) {
        log.error("UserNotFoundException occurred: ", ex);
        log.info("request: {}", request);

        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(HardSkillNotFoundException.class)
    @SneakyThrows
    ResponseEntity<Object> handleHardSkillNotFoundException(
            HardSkillNotFoundException ex,
            WebRequest request
    ) {
        log.error("HardSkillNotFoundException occurred: ", ex);
        log.info("request: {}", request);

        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserHardSkillAssociationAlreadyExistException.class)
    @SneakyThrows
    ResponseEntity<Object> handleUserHardSkillAssociationAlreadyExistException(
            UserHardSkillAssociationAlreadyExistException ex,
            WebRequest request
    ) {
        log.error("UserHardSkillAssociationAlreadyExistException occurred: ", ex);
        log.info("request: {}", request);

        return ResponseEntity.status(409).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserHardSkillAssociationNotFoundException.class)
    @SneakyThrows
    ResponseEntity<Object> handleUserHardSkillAssociationNotFoundException(
            UserHardSkillAssociationNotFoundException ex,
            WebRequest request
    ) {
        log.error("UserHardSkillAssociationNotFoundException occurred: ", ex);
        log.info("request: {}", request);

        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserHardSkillNotFoundException.class)
    @SneakyThrows
    ResponseEntity<Object> handleUserHardSkillNotFoundException(
            UserHardSkillNotFoundException ex,
            WebRequest request
    ) {
        log.error("UserHardSkillNotFoundException occurred: ", ex);
        log.info("request: {}", request);

        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(CategorySoftSkillNotFoundException.class)
    @SneakyThrows
    ResponseEntity<Object> handleCategorySoftSkillNotFoundException(
            CategorySoftSkillNotFoundException ex,
            WebRequest request
    ) {
        log.error("CategorySoftSkillNotFoundException occurred: ", ex);
        log.info("request: {}", request);

        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(SoftSkillNotFoundException.class)
    @SneakyThrows
    ResponseEntity<Object> handleSoftSkillNotFoundException(
            SoftSkillNotFoundException ex,
            WebRequest request
    ) {
        log.error("SoftSkillNotFoundException occurred: ", ex);
        log.info("request: {}", request);

        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserSoftSkillNotFoundException.class)
    @SneakyThrows
    ResponseEntity<Object> handleUserSoftSkillNotFoundException(
            UserSoftSkillNotFoundException ex,
            WebRequest request
    ) {
        log.error("UserSoftSkillNotFoundException occurred: ", ex);
        log.info("request: {}", request);

        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ProfessionNotFoundException.class)
    @SneakyThrows
    ResponseEntity<Object> handleProfessionNotFoundException(
            ProfessionNotFoundException ex,
            WebRequest request
    ) {
        log.error("ProfessionNotFoundException occurred: ", ex);
        log.info("request: {}", request);

        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }
}
