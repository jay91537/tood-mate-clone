package com.example.todo_api.friend;

import com.example.todo_api.friend.dto.FriendCreateRequest;
import com.example.todo_api.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendservice;

    @PostMapping
    public ResponseEntity<Void> createFriend(@RequestBody FriendCreateRequest request) throws Exception {
        Long friendId = friendservice.createFriend(request.getRequestMember().getId(), request.getResponseMember().getId());
        return ResponseEntity.created(URI.create("/friend/" + friendId)).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<Member>> getFriendList(@RequestBody Long memberId) throws Exception {
        List<Member> friendList = friendservice.getFriend(memberId);
        return ResponseEntity.ok().body(friendList);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long friendId) throws Exception {
        friendservice.deleteFriend(friendId);
        return ResponseEntity.noContent().build();
    }

}
