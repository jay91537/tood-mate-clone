package com.example.todo_api.member.dto;

import lombok.Getter;

@Getter
public class MemberUpdateRequest {
    private Long memberId;
    private String newLoginId;
}
