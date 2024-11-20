package com.example.todo_api.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class MemberCreateRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    private String loginId;

    @NotNull
    @NotBlank
    @NotEmpty
    private String loginPassword;
}
