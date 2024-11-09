package com.example.todo_api.member;

import com.example.todo_api.friend.FriendRepository;
import com.example.todo_api.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.jdbc.support.CustomSQLExceptionTranslatorRegistrar;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final FriendRepository friendRepository;

    // 회원가입 기능
    @Transactional
    public void createMember(String login_id, String login_password) throws Exception {

        // service단에서 여러 비즈니스 로직을 처리해 줄 텐데
        // 로그인, 비밀번호가 null인지 아닌지까지 판단하면 너무 무거워지지 않을까?

        try {
            Member member = new Member(login_id, login_password);
            memberRepository.save(member);
        } catch (IllegalArgumentException e) {
            // 예외를 처리하거나 사용자에게 적절한 에러 메시지를 전달
            throw new Exception("회원 생성 중 오류 발생: " + e.getMessage());
        }

        //어떤 비즈니스 로직이 있을지 생각해보기
        // 회원가입 완료
    }

    // 로그인 기능
    @Transactional(readOnly = true)
    public Member getMember(String login_id, String login_password) throws Exception {
        Member member = memberRepository.findByLoginIdAndPassword(login_id, login_password);
        if (member == null) {
            throw new Exception("존재하지 않는 계정입니다.");
        }
        return member;
    }

    // 아이디 수정 기능
    // 로그인을 한 상태에서 아이디 수정 버튼을 눌렀을 때 생기는일
    @Transactional
    public void updateMember(Long memberId, String newLoginId) throws Exception {

        Member member = memberRepository.findById(memberId);
        if(member == null) {
            throw new Exception("존재하지 않는 계정입니다.");
        }

        //아이디 겹치면 안되니까
        if(memberRepository.existsByLoginId(newLoginId)) {
            throw new Exception("이미 사용 중인 아이디입니다.");
        }

        member.updateLoginId(newLoginId);
    }

    // 계정 삭제 기능
    @Transactional
    public void deleteMember(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);

        if(member == null) {
            throw new Exception("존재하지 않는 계정입니다.");
        }

        memberRepository.deleteById(memberId);
    }
}
