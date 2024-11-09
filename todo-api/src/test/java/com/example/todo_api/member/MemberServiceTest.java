package com.example.todo_api.member;

import com.example.todo_api.todo.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    void createMemberTest_IfVlaid() throws Exception {
        // given
        //Member member = new Member("abcd", "1q2w");
        // when
        memberService.createMember("abcd","1q2w");

        // then
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void createMemberTest_WhenMemberIdIsNull() throws Exception {
        // Member 도메인에서 검증이 다 끝나므로 중복 검증을 더 할 필요없다...가 아닌가..?
        // 만약 Service Test에서 이걸 또 검증한다면 단일 책임 원칙에 위배되는것이라고 생각한다.
        //
        // given
        //Member member = new Member(null, "1q2w");

        // when
        Assertions.assertThatThrownBy(()-> {
            memberService.createMember(null, "1q2w");
        }).hasMessageContaining("회원 생성 중 오류 발생: 아이디가 null일 수 없습니다.").isInstanceOf(Exception.class);
        // then
    }

    @Test
    void getMemberTest_IfValid() throws Exception {
        // given
        BDDMockito.given(memberRepository.findByLoginIdAndPassword("abcd", "1q2w")).willReturn(new Member());

        // when
        Member findMember = memberService.getMember("abcd", "1q2w");

        // then
        verify(memberRepository, times(1)).findByLoginIdAndPassword(any(String.class), any(String.class));
    }

    @Test
    void getMemberTest_WhenMemberIsNull() throws Exception {
        //given
        BDDMockito.given(memberRepository.findByLoginIdAndPassword(anyString(), anyString())).willReturn(null);

        //when
        Assertions.assertThatThrownBy(()->{
            Member findMember = memberService.getMember("abcd", "1q2w");
        }).hasMessageContaining("존재하지 않는 계정입니다.").isInstanceOf(Exception.class);

        //then
    }

    @Test
    void updateMemberTest_IfValid() throws Exception {
        //given
        Member member = spy(new Member());
        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);
        BDDMockito.given(memberRepository.existsByLoginId("newId")).willReturn(false);

        //when
        memberService.updateMember(1L, "newId");

        //then
        verify(member, times(1)).updateLoginId(anyString());
    }

    @Test
    void updateMemebrTest_WhenMemberIsNull() throws Exception {
        //given
        BDDMockito.given(memberRepository.findById(anyLong())).willReturn(null);

        // 이거 주어지기 전에 일단 멤버 검증에서 널이 발생하니까 쓸모 없어서 그런건가? -> 맞음
        // BDDMockito.given(memberRepository.existsByLoginId("newId")).willReturn(false);

        //when
        Assertions.assertThatThrownBy(()-> {
            memberService.updateMember(999L, "newId");
        }).hasMessageContaining("존재하지 않는 계정입니다.").isInstanceOf(Exception.class);

        //then
    }

    @Test
    void updateMemberTest_WhenNewLoginIdExists() throws Exception {
        // given
        Member member = spy(new Member());

        // 멤버는 잘 가져왔지만
        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);

        // 이미 존재하는 아이디가 있음
        BDDMockito.given(memberRepository.existsByLoginId(anyString())).willReturn(true);
        // when
        Assertions.assertThatThrownBy(()-> {
            memberService.updateMember(1L, "anyId");
        }).hasMessageContaining("이미 사용 중인 아이디입니다.").isInstanceOf(Exception.class);

        //then
    }

    @Test
    void deleteMemberTest_IfValid() throws Exception {
        // given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(new Member());

        // when
        memberService.deleteMember(1L);

        //then
        verify(memberRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteMemberTest_WhenMemberIsNull() throws Exception {
        // given
        BDDMockito.given(memberRepository.findById(anyLong())).willReturn(null);

        // when
        Assertions.assertThatThrownBy(()-> {
            memberService.deleteMember(999L);
        }).hasMessageContaining("존재하지 않는 계정입니다.").isInstanceOf(Exception.class);

        // then
    }
}
