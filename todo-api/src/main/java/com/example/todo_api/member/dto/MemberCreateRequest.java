package com.example.todo_api.member.dto;

import lombok.Getter;

@Getter
public class MemberCreateRequest {
    private String loginId;
    private String loginPassword;
}
