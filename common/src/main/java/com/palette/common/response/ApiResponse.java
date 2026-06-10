package com.palette.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    Boolean result;
    Error error;
    T data;

    // 반환할 데이터가 없을 때 (로그아웃, 삭제 성공 등)
    public static <T> ResponseEntity<ApiResponse<T>> ok() {
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .result(true)
                        .build()
        );
    }

    // 반환할 데이터가 있을 때
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .result(true)
                        .data(data)
                        .build()
        );
    }

    // 실패 응답
    public static <T> ResponseEntity<ApiResponse<T>> fail(HttpStatus httpStatus, String errorCode,
                                                          String errorMessage) {
        return ResponseEntity.status(httpStatus)
                .body(ApiResponse.<T>builder()
                        .result(false)
                        .error(Error.of(errorCode, errorMessage))
                        .build());
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Error(String errorCode, String errorMessage) {
        public static Error of(String errorCode, String errorMessage) {
            return new Error(errorCode, errorMessage);
        }
    }
}