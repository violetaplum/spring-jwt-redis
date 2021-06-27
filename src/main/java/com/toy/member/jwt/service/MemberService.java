package com.toy.member.jwt.service;

import com.toy.member.jwt.model.Member;

public interface MemberService {

    void checkMemberId(String memberId);

    void saveMember(Member member);
}
