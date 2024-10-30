package com.example.todo_api.friend;

import com.example.todo_api.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member request_member;

    @JoinColumn(name = "friend_member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member Response_member;

    public Friend(Member request_member, Member response_member) {
        this.request_member = request_member;
        this.Response_member = response_member;
    }
}
