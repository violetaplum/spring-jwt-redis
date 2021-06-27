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
                // 기본기능이 Optional.ifPresent
                // 아이디 중복체크
                .ifPresent(s -> {
                    throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
                });
    }

    // db 로 저장시켜주는 마무리작업
    // 트랜잭션 최종 날려주는놈
    // 안달면 db 로 트랜잭션 안날아가고 영속성 컨텍스트에 저장될뿐 실제 적용 안됨
    @Transactional
    @Override
    public void saveMember(Member member) {

        memberRepository.save(member);
    }
}
