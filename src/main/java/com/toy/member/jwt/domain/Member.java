package com.toy.member.jwt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table(name = "MEMBER")
// where deleted = 'false' 인 애들만 가져온다는 말
// 유저가 탈퇴해도 flag 만 변경하고 정보는 남기기 위해 추가됨
@Where(clause = "DELETED = false")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_SEQ")
    private Long memberSeq;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "MEMBER_PASSWORD")
    private String memberPassword;

    @Column(name = "EMAIL")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;

    @Column(name = "DELETED")
    private Boolean deleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private RoleType type;

    @PrePersist
    public void prePersist() {
        this.regDate = LocalDateTime.now();
        this.modDate = LocalDateTime.now();
        this.deleted = false;
    }

    public enum RoleType{
        ROLE_USER,
        ROLE_ADMIN,
    }
}
