package com.example.todo_api.todo;

import com.example.todo_api.common.exception.BadRequestException;
import com.example.todo_api.common.message.ErrorMessage;
import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createTodo(String content, Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);

        if(member == null){
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXISTS);
        }

        Todo todo = new Todo(content, member);
        todoRepository.save(todo);
        return todo.getId();
    }

    @Transactional(readOnly = true)
    public List<Todo> getTodoList(Long memberId) throws Exception{
        Member member = memberRepository.findById(memberId);
        if(member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXISTS);
        }
        return todoRepository.findAllByMember(member);
    }

    @Transactional
    public void updateTodo(Long todoId, Long memberId, String updateContent) throws Exception {
        Todo todo = todoRepository.findById(todoId);
        Member member = memberRepository.findById(memberId);

        if (todo == null) {

            throw new Exception(ErrorMessage.TODO_NOT_EXISTS);
        }

        if (member == null) {

            throw new Exception(ErrorMessage.MEMBER_NOT_EXISTS);
        }

        if(todo.getMember() != member){

            throw new Exception("할일은 생성한 유저만 수정할 수 있습니다.");
        }

        todo.updateContent(updateContent);
    }

    @Transactional
    public void deleteTodo(Long todoId) throws Exception{
        Todo todo = todoRepository.findById(todoId);

        if (todo == null) {

            throw new Exception(ErrorMessage.TODO_NOT_EXISTS);
        }

        todoRepository.deleteById(todoId);
    }

}
