package com.example.todo_api.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FriendGetRequest {
    @NotNull
    private Long id;

    @NotNull
    private Long friendId;
}
