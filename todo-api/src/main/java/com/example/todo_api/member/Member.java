package com.example.todo_api.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 20, name = "member_login_id")
    private String login_id;

    @Column(length = 20, name = "member_password")
    private String login_password;


    public Member(String loginId, String loginPassword) throws Exception{
        this.login_id = loginId;
        this.login_password = loginPassword;

        validateLogin(loginId);

        validatePassword(loginPassword);
    }

    private void validateLogin(String loginId) throws ClassNotFoundException {
        if (login_id == null) {
            throw new ClassNotFoundException("아이디가 null일 수 없습니다.");
        }
        else if (login_id.isBlank()) {
            throw new IllegalArgumentException("아이디가 빈칸이거나 공백일 수 없습니다.");
        }
        else if (login_id.contains(" ")) {
            throw new IllegalArgumentException("아이디의 중간에 공백이 들어갈 수 없습니다.");
        }
    }

    private void validatePassword(String loginPassword) throws Exception {
        if (login_password == null) {
            throw new IllegalArgumentException("비밀번호가 null일 수 없습니다.");
        }
        else if (login_password.isBlank()) {
            throw new IllegalArgumentException("비밀번호가 빈칸이거나 공백일 수 없습니다.");
        }
        else if (login_password.contains(" ")) {
            throw new IllegalArgumentException("비밀번호의 중간에 공백이 들어갈 수 없습니다.");
        }
    }


    public void updateLoginId(String newLoginId) {
        this.login_id = newLoginId;
    }


}
