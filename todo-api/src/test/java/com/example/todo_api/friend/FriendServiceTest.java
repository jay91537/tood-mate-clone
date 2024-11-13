package com.example.todo_api.friend;

import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FriendServiceTest {
    @Mock
    MemberRepository memberRepository;

    @Mock
    FriendRepository friendRepository;

    @InjectMocks
    FriendService friendService;

    @Test
    void createFriendTest_IfValid() throws Exception {
        // given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(new Member());
        BDDMockito.given(memberRepository.findById(2L)).willReturn(new Member());

        // when
        friendService.createFriend(1L, 2L);

        // then
        verify(friendRepository, times(1)).save(any(Friend.class));
    }

    @Test
    void createFriendTest_WhenRequestMemberIdOrResponseMemberIdDoNotExist() throws Exception {
        // given
        BDDMockito.given(memberRepository.findById(anyLong())).willReturn(null);

        // when
        Assertions.assertThatThrownBy(()-> {
            friendService.createFriend(999L, 22L);
        }).hasMessageContaining("존재하지 않는 계정입니다.").isInstanceOf(Exception.class);

        // then
    }

    @Test
    void createFriendTest_WhenFriendRelationAlreadyExists() throws Exception {
        //given
        Member request_member = spy(new Member());
        Member response_member = spy(new Member());

        BDDMockito.given(memberRepository.findById(1L)).willReturn(request_member);
        BDDMockito.given(memberRepository.findById(2L)).willReturn(response_member);

        BDDMockito.given(friendRepository.existsByMembers(request_member, response_member)).willReturn(true);

        // when

        Assertions.assertThatThrownBy(()-> {
            friendService.createFriend(1L, 2L);
        }).hasMessageContaining("이미 존재하는 친구 관계입니다.").isInstanceOf(Exception.class);

        // then
    }

    @Test
    void getFriendTest_IfValid() throws Exception {
        // given
        Member member1 = spy(new Member());
        Member member2 = spy(new Member());
        Member member3 = spy(new Member());

        BDDMockito.given(memberRepository.findById(1L)).willReturn(member1);
        BDDMockito.given(memberRepository.findById(2L)).willReturn(member2);
        BDDMockito.given(memberRepository.findById(3L)).willReturn(member3);

        friendService.createFriend(1L, 2L);
        friendService.createFriend(1L, 3L);

        List<Friend> friendRelations = List.of(
                new Friend(member1, member2),
                new Friend(member1, member3)
        );

        BDDMockito.given(friendRepository.findByMember(member1)).willReturn(friendRelations);

        // when
        List<Member> friendList = friendService.getFriend(1L);

        // then
        verify(friendRepository, times(1)).findByMember(any(Member.class));
        Assertions.assertThat(friendList).hasSize(2);
    }

    @Test
    void getMemberTest_WhenMemberDoesNotExits() throws Exception {
        // given
        BDDMockito.given(memberRepository.findById(anyLong())).willReturn(null);

        // when
        Assertions.assertThatThrownBy(()-> {
            List<Member> getFriend = friendService.getFriend(999L);
        }).hasMessageContaining("존재하지 않는 계정입니다.").isInstanceOf(Exception.class);

        // then
    }

    @Test
    void getMemberTest_WhenFriendRelationListDoesNotExists() throws Exception {
        // given
        Member member1 = spy(new Member());

        BDDMockito.given(memberRepository.findById(1L)).willReturn(member1);

        BDDMockito.given(friendRepository.findByMember(member1)).willReturn(null);

        // when
        Assertions.assertThatThrownBy(()-> {
            List<Member> getFriend = friendService.getFriend(1L);
        }).hasMessageContaining("친구가 존재하지 않습니다.").isInstanceOf(Exception.class);

        // then
    }

    @Test
    void deleteFriend_IfValid() throws Exception {
        // given
        Member member1 = spy(new Member());
        Member member2 = spy(new Member());
        Friend friend = spy(new Friend());

        BDDMockito.given(memberRepository.findById(1L)).willReturn(member1);
        BDDMockito.given(memberRepository.findById(2L)).willReturn(member2);
        BDDMockito.given(friendRepository.findByRequestAndResponseMember(member1, member2)).willReturn(friend);

        // when
        friendService.deleteFriend(1L);

        // then
        verify(friendRepository, times(1)).findByRequestAndResponseMember(any(Member.class), any(Member.class));
        verify(friendRepository, times(1)).delete(any(Friend.class));
    }
}
