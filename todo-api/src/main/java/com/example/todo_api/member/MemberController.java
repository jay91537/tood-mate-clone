package com.example.todo_api.member;

import com.example.todo_api.member.dto.MemberCreateRequest;
import com.example.todo_api.member.dto.MemberGetRequest;
import com.example.todo_api.member.dto.MemberUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;


    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody @Valid MemberCreateRequest request) throws Exception {
        Long memberId = memberService.createMember(request.getLoginId(), request.getLoginPassword());

        return ResponseEntity.created(URI.create("/member/" + memberId)).build();
    }

    @GetMapping("/")
    public ResponseEntity<Long> getMember(@RequestBody @Valid MemberGetRequest request) throws Exception {

        Member member = memberService.getMember(request.getLoginId(), request.getLoginPassword());

        return ResponseEntity.ok().body(member.getId());
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) throws Exception {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(@PathVariable Long memberId, @RequestBody @Valid MemberUpdateRequest request) throws Exception {
        memberService.updateMember(request.getMemberId(), request.getNewLoginId());
        return ResponseEntity.ok().build();
    }
}

