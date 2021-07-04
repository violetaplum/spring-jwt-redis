package com.toy.member.jwt.repository;

import com.toy.member.jwt.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
// 기본적으로 'member_id 로 member 를 찾아라' 로 인식

    // 'Optional' 은
    // Member null 일수도 있는데 이때 Member 껍데기를 생성해준다
    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findByMemberName(String memberName);

}
