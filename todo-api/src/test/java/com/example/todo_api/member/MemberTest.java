package com.example.todo_api.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class MemberTest {

    @DisplayName("생성된 사용자의 로그인 아이디가 null인 경우 예외 발생 여부")
    @Test
    void createWith_Null_LoginId() {
        assertThatThrownBy(() -> {
            new Member(null, "1q2w3e4r");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성된 사용자의 로그인 아이디가 공란일 경우 예외 발생 여부")
    @Test
    void createWith_Blank_LoginId() {
        assertThatThrownBy(() ->{
            new Member("", "1q2w3e4r" );
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성된 사용자의 로그인 아이디에 공란이 포함될 경우 예외 발생 여부")
    @Test
    void createWith_Include_Blank_LoginId() {
        assertThatThrownBy(() ->{
            new Member("ab cd", "1q2w3e4r");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성된 사용자의 로그인 비밀번호가 null인 경우 예외 발생 여부")
    @Test
    void createWith_Null_Password() {
        assertThatThrownBy(() -> {
            new Member("abcd", null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성된 사용자의 로그인 비밀번호가 공란일 경우 예외 발생 여부")
    @Test
    void createWith_Blank_Password() {
        assertThatThrownBy(() ->{
            new Member("abcd", "");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성된 사용자의 로그인 비밀번호에 공란이 포함될 경우 예외 발생 여부")
    @Test
    void createWith_Include_Blank_Password() {
        assertThatThrownBy(() ->{
            new Member("abcd", "1q2w 3e4r");
        }).isInstanceOf(IllegalArgumentException.class);
    }

}
