package com.toy.member.jwt.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {

    private String memberId;

    private String memberPassword;

    private String memberName;

    private String email;

}
