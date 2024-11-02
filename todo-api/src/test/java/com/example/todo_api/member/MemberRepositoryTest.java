package com.example.todo_api.member;

import com.example.todo_api.friend.FriendRepository;
import com.example.todo_api.todo.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberRepositoryTest {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @Transactional
    @Rollback(false)
    void createMember() {
        //given
        Member member = new Member("abcdef", "1q2w3e4r");
        memberRepository.save(member);
        //when

        //then
        Assertions.assertThat(member.getId()).isNotNull();
    }

    @Test
    @Transactional
    void memberFindOneByIdTest() {
        //given
        Member member = new Member("abcd", "1q2w3e4r");
        memberRepository.save(member);

        memberRepository.flushAndClear();
        //when
        Member findMember = memberRepository.findById(member.getId());
        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    }

    @Test
    @Transactional
    void memberFindAllTest() {
        Member member1 = new Member("abcd", "1q2w3e4r");
        Member member2 = new Member("efgh", "1q2w3e4r");
        Member member3 = new Member("ijkl", "1q2w3e4r");
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> memberList = memberRepository.findAll();

        Assertions.assertThat(memberList).hasSize(3);
    }

    @Test
    @Transactional
    @Rollback(false)
    void memberUpdateTest() {
        Member member = new Member("abcd", "1q2w3e4r");
        memberRepository.save(member);

        memberRepository.flushAndClear();

        Member findMember = memberRepository.findById(member.getId());
        findMember.updateLoginId("efgh");
    }

    @Test
    @Transactional
    @Rollback(false)
    void memberRemoveTest() {
        Member member1 =new Member("abcd", "1q2w3e4r");
        Member member2 = new Member("efgh", "1q2w3e4r");

        memberRepository.save(member1);
        memberRepository.save(member2);

        memberRepository.flushAndClear();

        memberRepository.removeByID(member1.getId());
    }
}
