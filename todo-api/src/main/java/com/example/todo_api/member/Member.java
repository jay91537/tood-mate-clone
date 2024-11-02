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

    public Member(String login_id, String login_password) {
        this.login_id = login_id;
        this.login_password = login_password;
    }

    public void updateLoginId(String newLoginId) {
        this.login_id = newLoginId;
    }


}
