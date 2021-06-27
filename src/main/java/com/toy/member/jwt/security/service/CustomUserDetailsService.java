package com.toy.member.jwt.security.service;

import com.toy.member.jwt.model.Member;
import com.toy.member.jwt.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("find member {}", username);
        Member member = repository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not found user" + username));

        log.info("Success find member {}", member);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getType().name()));
        MemberContext memberContext = new MemberContext(member,roles);

        return memberContext;
    }
}
