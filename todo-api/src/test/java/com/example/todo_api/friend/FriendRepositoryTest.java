package com.example.todo_api.friend;

import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import com.example.todo_api.todo.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FriendRepositoryTest {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @Transactional
    @Rollback(false)
    void createFriend() {
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        memberRepository.save(member1);
        memberRepository.save(member2);

        Friend friend = new Friend(member1, member2);
        //when
        friendRepository.save(friend);

        //then
        Assertions.assertThat(friend.getId()).isNotNull();
    }

    @Test
    @Transactional
    void friendFindByMember() {
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        memberRepository.save(member1);
        memberRepository.save(member2);
        // -- 영속성 컨텍스트에 멤버1, 멤버2 저장
        Friend friend = new Friend(member1, member2);
        //when
        friendRepository.save(friend);
        // -- 영속성 컨텍스트에 friend(친구 관계) 저장
        List<Friend> friendList1 = friendRepository.findByMember(member1);
        List<Friend> friendList2 = friendRepository.findByMember(member2);
        //then
        Assertions.assertThat(friendList1).isEqualTo(friendList2);
    }

    @Test
    @Transactional
    void FriendDeleteTest() {
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        memberRepository.save(member1);
        memberRepository.save(member2);

        Friend friend = new Friend(member1, member2);
        //when
        friendRepository.save(friend);

        friendRepository.flushAndClear();

        List<Friend> friendList = friendRepository.findByMember(member1);

        friendList.remove(friendList.get(0));

        Assertions.assertThat(friendList).hasSize(0);


        //then
    }

    @AfterAll
    public static void doNotFinish() {
        System.out.println("test finished");
        while (true) {}
    }

}
