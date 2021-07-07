package com.toy.member.jwt.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberLoginDto {

    private String username;

    private String password;

}
