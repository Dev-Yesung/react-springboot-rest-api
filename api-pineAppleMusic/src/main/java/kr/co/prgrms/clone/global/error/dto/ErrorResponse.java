package kr.co.prgrms.clone.global.error.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ToString
@Getter
public final class ErrorResponse {

    private Integer status;
    private String message;
    private Map<String, String> errorMap;

    @Builder
    private ErrorResponse(Integer status, String message, List<FieldError> fieldErrors) {
        this.status = status;
        this.message = message;
        this.errorMap = fieldErrors != null ? errorMap(fieldErrors) : new HashMap<>();
    }

    private Map<String, String> errorMap(List<FieldError> fieldErrors) {
        return fieldErrors
                .stream()
                .filter(e -> e.getDefaultMessage() != null)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
