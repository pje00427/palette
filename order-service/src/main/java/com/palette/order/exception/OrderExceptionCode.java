package com.palette.order.exception;

import com.palette.common.exception.DomainExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderExceptionCode implements DomainExceptionCode {

  // 주문
  NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
  ALREADY_CANCELLED(HttpStatus.BAD_REQUEST, "이미 취소된 주문입니다."),
  CANNOT_CANCEL_ORDER(HttpStatus.BAD_REQUEST, "배송 중인 주문은 취소할 수 없습니다."),

  // 결제
  NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "결제 정보를 찾을 수 없습니다."),
  PAYMENT_ALREADY_COMPLETED(HttpStatus.CONFLICT, "이미 완료된 결제입니다."),
  PAYMENT_FAILED(HttpStatus.BAD_REQUEST, "결제에 실패했습니다.");

  private final HttpStatus status;
  private final String message;
}