package com.palette.inventory.exception;

import com.palette.common.exception.DomainExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InventoryExceptionCode implements DomainExceptionCode {

  // 재고
  NOT_FOUND_INVENTORY(HttpStatus.NOT_FOUND, "재고 정보를 찾을 수 없습니다."),
  OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족합니다."),
  ALREADY_EXISTS_INVENTORY(HttpStatus.CONFLICT, "이미 등록된 재고입니다."),

  // 동시성
  INVENTORY_LOCK_FAILED(HttpStatus.CONFLICT, "재고 처리 중 충돌이 발생했습니다. 다시 시도해주세요.");

  private final HttpStatus status;
  private final String message;
}