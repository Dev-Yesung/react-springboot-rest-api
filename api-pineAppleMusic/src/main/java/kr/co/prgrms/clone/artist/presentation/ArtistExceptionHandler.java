package kr.co.prgrms.clone.artist.presentation;

import kr.co.prgrms.clone.artist.exception.ArtistException;
import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.global.error.dto.ErrorResponse;
import kr.co.prgrms.clone.song.exception.SongException;
import kr.co.prgrms.clone.user.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static kr.co.prgrms.clone.global.error.ErrorCode.INVALID_PARAMETER_VALUE;

@RestControllerAdvice(basePackageClasses = ArtistController.class)
public class ArtistExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleNotValidMethodArguments(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(INVALID_PARAMETER_VALUE.getStatus())
                .message(INVALID_PARAMETER_VALUE.getMessage())
                .fieldErrors(fieldErrors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArtistException.class)
    public ResponseEntity<ErrorResponse> handleUserExceptions(ArtistException e) {
        ErrorCode errorCode = e.getErrorCode();
        int status = errorCode.getStatus();
        String message = errorCode.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status)
                .message(message)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
