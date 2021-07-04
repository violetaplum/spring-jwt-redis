package com.toy.member.jwt.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberLoginRequest {

    private String username;

    private String password;

}
