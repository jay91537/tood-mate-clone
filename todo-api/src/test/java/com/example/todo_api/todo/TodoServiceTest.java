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

import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    void createTodoTest_IfValid() throws Exception {
        //given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(new Member());
        // memberRepository의 가짜객체의 동작을 우리가 지정

        //when
        todoService.createTodo("content", 1L);
        //then
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void createTodoTest_When_MemberIdDoesNotExist() throws Exception {
        //given
        BDDMockito.given(memberRepository.findById(anyLong())).willReturn(null);
        //when
        Assertions.assertThatThrownBy(()->{
            todoService.createTodo("content", 9999L);
        }).hasMessageContaining("존재하지 않는 Id입니다.").isInstanceOf(Exception.class);
    }

    @Test
    void getTodoList_IfValid() throws Exception {
        // given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(new Member());
        // memberRepository의 가짜객체의 동작을 우리가 지정

        // when
        List<Todo> todo = todoService.getTodoList(1L);
        // then
        verify(todoRepository, times(1)).findAllByMember(any(Member.class));
    }

    @Test
    void getTodoList_When_MemberIdDoesNotExist() throws Exception {
        // given
        BDDMockito.given(memberRepository.findById(anyLong())).willReturn(null);

        // when
        Assertions.assertThatThrownBy(() ->{
            List<Todo> todo = todoService.getTodoList(1L);
        }).hasMessageContaining("존재하지 않는 Id입니다.").isInstanceOf(Exception.class);

        // then
    }

    @Test
    void updateTodo_IfValid_ALl() throws Exception {
        // given
        Member member = spy(new Member());
        Todo todo = spy(new Todo("content", member));

        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);
        BDDMockito.given(todoRepository.findById(0L)).willReturn(todo);

        //when
        todoService.updateTodo(0L, 1L, "newcontent");

        //then
        verify(todo, times(1)).updateContent(any(String.class));
    }

    @Test
    void updateTodo_WhenTodoIdDoesNotExist() throws Exception{
        // todo객체와 member객체는 매칭 되어있다고 가정
        // given
        //Member member = spy(new Member());
        //Todo todo = spy(new Todo("content", member));

        BDDMockito.given(memberRepository.findById(1L)).willReturn(new Member());
        BDDMockito.given(todoRepository.findById(anyLong())).willReturn(null);

        // when
        Assertions.assertThatThrownBy(() ->{
            todoService.updateTodo(999L, 1L, "newContent");
        }).hasMessageContaining("존재하지 않는 todoId입니다.").isInstanceOf(Exception.class);

        // then
    }

    @Test
    void updateTodo_WhenMemberIdDoesNotExist() throws Exception {
        // todo객체와 member객체는 매칭 되어있다고 가정
        // given
        BDDMockito.given(memberRepository.findById(anyLong())).willReturn(null);
        BDDMockito.given(todoRepository.findById(1L)).willReturn(new Todo());

        // when
        Assertions.assertThatThrownBy(() ->{
            todoService.updateTodo(1L, 999L, "newContent");
        }).hasMessageContaining("존재하지 않는 memberId입니다.").isInstanceOf(Exception.class);

        // then
    }

    @Test
    void updateTodo_whenMemberTodoDoesNotMatch() throws Exception {
        // given
        Member member = new Member();
        Todo todo = new Todo();

        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);
        BDDMockito.given(todoRepository.findById(0L)).willReturn(todo);

        // when
        Assertions.assertThatThrownBy(() ->{
            todoService.updateTodo(0L, 1L, "newcontent");
        }).hasMessageContaining("할일은 생성한 유저만 수정할 수 있습니다.").isInstanceOf(Exception.class);

        // then
    }

    @Test
    void deleteTodo_IfValid() throws Exception {
        // given
        BDDMockito.given(todoRepository.findById(1L)).willReturn(new Todo());
        //Long todoId = 1L;

        // when
        todoService.deleteTodo(1L);

        // then
        verify(todoRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    void deleteTodo_WhenTodoIdDoesNotExist() throws Exception {
        //given
        BDDMockito.given(todoRepository.findById(anyLong())).willReturn(null);

        //when
        Assertions.assertThatThrownBy(()->{
            todoService.deleteTodo(999L);
        }).hasMessageContaining("존재하지 않는 todoId입니다.").isInstanceOf(Exception.class);

        //then
    }
}
