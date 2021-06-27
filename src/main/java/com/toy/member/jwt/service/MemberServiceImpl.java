package com.toy.member.jwt.service;

import com.toy.member.jwt.model.Member;
import com.toy.member.jwt.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public void checkMemberId(String memberId){
        memberRepository.findByMemberId(memberId)
                .ifPresent(s -> {
                    throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
                });
    }

    @Transactional
    @Override
    public void saveMember(Member member) {

        memberRepository.save(member);
    }
}
