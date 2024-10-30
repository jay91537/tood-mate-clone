package com.example.todo_api.todo;

import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    public void createTodoTest() throws Exception {
        //given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(new Member());
        //when
        todoService.createTodo("content", 1L);
        //then
        Mockito.verify(todoRepository,Mockito.times(1) ).save(Mockito.any(Todo.class));
    }

    @Test
    public void createTodoTest_When_MemberDoesNotExist() throws Exception {
        //given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(null);
        //when
        Assertions.assertThatThrownBy(()->{
            todoService.createTodo("content", 9999L);
        }).hasMessageContaining("존재하지 않는 Id입니다.").isInstanceOf(Exception.class);

        //then
        Mockito.verify(todoRepository,Mockito.times(1) ).save(Mockito.any(Todo.class));
    }


}
