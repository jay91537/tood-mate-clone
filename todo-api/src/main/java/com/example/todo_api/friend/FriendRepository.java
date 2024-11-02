package com.example.todo_api.friend;

import com.example.todo_api.member.Member;
import com.example.todo_api.todo.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class FriendRepository {

    @PersistenceContext
    private EntityManager em;

    // 친구 관계 생성
    public void save(Friend friend){
        em.persist(friend);
    }

    // 친구 관계 삭제
    public void delete(Friend friend) {em.remove(friend);}

    // 친구 관계 조회
    // 우리는 인자로 받은 member의 friend 관계를 찾을거예요
    public List<Friend> findByMember(Member member) {
        return em.createQuery("select f from Friend f where f.request_member = :member_check or f.Response_member = :member_check", Friend.class)
                .setParameter("member_check", member)
                .getResultList();
    }

    // 테스트 용도로만 사용
    public void flushAndClear() {
        em.flush();
        em.clear();
    }
}
