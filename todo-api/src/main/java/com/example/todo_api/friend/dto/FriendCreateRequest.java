package com.example.todo_api.friend.dto;

import com.example.todo_api.member.Member;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FriendCreateRequest {

    @NotNull
    private Member requestMember;

    @NotNull
    private Member responseMember;
}
