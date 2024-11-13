package com.example.todo_api.member.dto;

import lombok.Getter;

@Getter
public class MemberGetRequest {
    private String loginId;
    private String loginPassword;
}
