package com.example.todo_api.member;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 20, name = "member_login_id")
    private String login_id;

    @Column(length = 20, name = "member_password")
    private String login_password;

}
