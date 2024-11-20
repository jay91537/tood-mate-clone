package com.example.todo_api.member.dto;

import com.example.todo_api.common.message.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberUpdateRequest {
    @NotNull(message = ErrorMessage.MEMBER_ID_MUST_BE_NOT_NULL)
    private Long memberId;

    @NotNull
    @NotBlank
    @NotEmpty
    private String newLoginId;
}
