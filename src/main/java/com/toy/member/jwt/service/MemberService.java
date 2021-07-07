package com.toy.member.jwt.service;

import com.toy.member.jwt.domain.Member;

public interface MemberService {

    void checkMemberId(String memberId);

    void saveMember(Member member);

    Member loginMember(String username, String password);
}
