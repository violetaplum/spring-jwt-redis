package com.toy.member.jwt.service;

import com.toy.member.jwt.domain.Member;
import com.toy.member.jwt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

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

    @Transactional(readOnly = true)
    @Override
    public Member loginMember(String username, String password) {
        Member member = memberRepository.findByMemberId(username).orElseThrow(() ->
                new IllegalArgumentException("아이디 혹은 비밀번호가 일치하지않습니다.")
        );
        Boolean match = passwordEncoder.matches(password, member.getMemberPassword());

        if(match.equals(false)){
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 일치하지않습니다.");
        }

        return member;
    }
}
