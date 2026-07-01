// product-service/src/main/java/com/palette/product/domain/drop/dto/request/DropNotificationCreateRequest.java

package com.palette.product.domain.drop.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DropNotificationCreateRequest {

    @NotNull(message = "드롭 ID는 필수입니다.")
    private Long dropId;

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;
}