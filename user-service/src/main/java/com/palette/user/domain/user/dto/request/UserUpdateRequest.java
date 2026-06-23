package com.palette.user.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private String name;
    private String phone;
    private String address;
}