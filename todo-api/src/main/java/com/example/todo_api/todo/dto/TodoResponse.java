package com.example.todo_api.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoResponse {
    private Long memberId;
    private String content;
}
