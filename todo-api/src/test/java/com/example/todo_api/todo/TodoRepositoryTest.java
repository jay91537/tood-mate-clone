package com.example.todo_api.todo;

import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    void todoSaveTest() {
        // 트랜잭션의 시작

        Todo todo = new Todo("todo_content", null);
        todoRepository.save(todo);

        //트랜잭션 종 => 커밋
        //에러가 발생 했을 때 자동으로 롤백


        Assertions.assertThat(todo.getId()).isNotNull();
        //테스트 환경 기준으로는 에러가 나지 않아도 테스트 끝나면 자동으로 롤백 해준다. => 수동으로 롤백 꺼주면 됨.
    }

    @Test
    @Transactional
    void todoFindOneByIdTest() {
        // given
        Todo todo = new Todo("todo_content",  null);
        todoRepository.save(todo);

        todoRepository.flushAndClear();

        // when
        Todo findTodo = todoRepository.findById(todo.getId());
        // then
        Assertions.assertThat(findTodo.getId()).isEqualTo(todo.getId());
    }

    @Test
    @Transactional
    void todoFindAllTest() {
        Todo todo1 = new Todo("todo_content1",  null);
        Todo todo2 = new Todo("todo_content2", null);
        Todo todo3 = new Todo("todo_content3",  null);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        List<Todo> todoList = todoRepository.findAll();

        System.out.println(todoList);

        Assertions.assertThat(todoList).hasSize(3);
    }

    @Test
    @Transactional
    void todoFindALlByMemberTest() {
        Member member1 = new Member();
        Member member2 = new Member();
        memberRepository.save(member1);
        memberRepository.save(member2);

        Todo todo1 = new Todo("todo_content1",  member1);
        Todo todo2 = new Todo("todo_content2",  member1);
        Todo todo3 = new Todo("todo_content3", member2);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        List<Todo> member1TodoList = todoRepository.findAllByMember(member1);
        List<Todo> member2TodoList = todoRepository.findAllByMember(member2);

        Assertions.assertThat(member1TodoList).hasSize(2);
        Assertions.assertThat(member2TodoList).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback(false)
    void todoUpdateTest() {
        Todo todo1 = new Todo("todo_content1", null);
        todoRepository.save(todo1);

        todoRepository.flushAndClear();

        Todo findTodo1 = todoRepository.findById(todo1.getId());
        findTodo1.updateContent("new content");
    }

    @Test
    @Transactional
    @Rollback(false)
    void todoRemoveTest() {
        //given
        Todo todo = new Todo("to remove todo", null);
        todoRepository.save(todo);
        todoRepository.save(new Todo("content1",  null));
        todoRepository.save(new Todo("content2", null));
        //when
        todoRepository.deleteById(todo.getId());
        //then
    }


    @AfterAll
    public static void doNotFinish() {
        System.out.println("test finished");
        while(true) {}
    }
}