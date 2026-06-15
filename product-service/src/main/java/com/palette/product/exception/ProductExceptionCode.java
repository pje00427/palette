package com.palette.product.exception;

import com.palette.common.exception.DomainExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductExceptionCode implements DomainExceptionCode {

    // 아티스트
    NOT_FOUND_ARTIST(HttpStatus.NOT_FOUND, "아티스트를 찾을 수 없습니다."),

    // 카테고리
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    NOT_FOUND_SUB_CATEGORY(HttpStatus.NOT_FOUND, "서브 카테고리를 찾을 수 없습니다."),
    DUPLICATE_SUB_CATEGORY(HttpStatus.CONFLICT, "이미 존재하는 서브 카테고리입니다."),

    // 상품
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT_OPTION(HttpStatus.NOT_FOUND, "상품 옵션을 찾을 수 없습니다."),

    // 드롭
    NOT_FOUND_DROP(HttpStatus.NOT_FOUND, "드롭을 찾을 수 없습니다."),
    DROP_NOT_OPEN(HttpStatus.BAD_REQUEST, "드롭이 오픈 상태가 아닙니다."),
    DROP_SOLD_OUT(HttpStatus.BAD_REQUEST, "드롭이 매진되었습니다."),
    ALREADY_NOTIFIED(HttpStatus.CONFLICT, "이미 사전 알림 신청을 하셨습니다.");

    private final HttpStatus status;
    private final String message;
}