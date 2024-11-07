package com.example.todo_api.friend;

import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import com.example.todo_api.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createFriend(Member request_member, Member response_member) throws Exception{
        Friend friend = new Friend(request_member, response_member);

        List<Friend> friendList = friendRepository.findByMember(request_member);
        if(friendList==null) {
            throw new Exception("");
        }
        friendRepository.save(friend);
    }
}
