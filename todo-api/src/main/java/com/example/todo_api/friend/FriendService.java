package com.example.todo_api.friend;

import com.example.todo_api.common.exception.BadRequestException;
import com.example.todo_api.common.message.ErrorMessage;
import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import com.example.todo_api.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createFriend(Long request_memberId, Long response_memberId) throws Exception{

        if (memberRepository.findById(request_memberId) == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_ID_MUST_BE_NOT_NULL);
        }
        if (memberRepository.findById(response_memberId) == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_ID_MUST_BE_NOT_NULL);
        }

        Member request_member = memberRepository.findById(request_memberId);
        Member response_member = memberRepository.findById(response_memberId);

        // 양방향으로 친구관계 존재하는지 확인
        if (friendRepository.existsByMembers(request_member, response_member)) {
            throw new Exception("이미 존재하는 친구 관계입니다.");
        }

        Friend friend = new Friend(request_member, response_member);
        friendRepository.save(friend);

        return friend.getId();
    }

    // 어떤 멤버의 친구명단을 모두 가져옴
    @Transactional(readOnly = true)
    public List<Member> getFriend(Long memberId) throws Exception {

        Member member = memberRepository.findById(memberId);

        if(member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_ID_MUST_BE_NOT_NULL);
        }

        List<Friend> friendRelationList = friendRepository.findByMember(member);

        if(friendRelationList.isEmpty()) {
            throw new Exception("친구가 존재하지 않습니다.");
        }

        List<Member> friendList = new ArrayList<>();

        for (Friend friendRelation : friendRelationList) {

            if (friendRelation.getRequest_member().equals(member)) {
                friendList.add(friendRelation.getResponse_member());
            } else if (friendRelation.getResponse_member().equals(member)) {
                friendList.add(friendRelation.getRequest_member());
            }
        }

        // nullPoinerException이 발동된다..
        /*
        while (friendRelationList.get(i).getRequest_member() == member) {
            friendList.add(friendRelationList.get(i).getResponse_member());
            i++;
        }
        */

        return friendList;
    }

    // 친구관계를 끊어버리는거
    @Transactional
    public void deleteFriend(Long friendId) throws Exception {

        Friend friend = friendRepository.findById(friendId);

        // Member requestMember = memberRepository.findById(friend.getRequest_member().getId());
        // Member responseMember = memberRepository.findById(friend.getResponse_member().getId());

        // Friend deleteFriendList = friendRepository.findByRequestAndResponseMember(requestMember, responseMember);

        friendRepository.delete(friend);
    }
}
