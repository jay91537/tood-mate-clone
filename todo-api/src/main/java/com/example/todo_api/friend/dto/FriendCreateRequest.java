package com.example.todo_api.friend.dto;

import com.example.todo_api.member.Member;
import lombok.Getter;

@Getter
public class FriendCreateRequest {
    private Member requestMember;
    private Member responseMember;
}
