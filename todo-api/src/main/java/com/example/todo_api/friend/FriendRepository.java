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
    // 단순 찾기
    public List<Friend> findByMember(Member member) {
        return em.createQuery("select f from Friend f where f.request_member = :member_check or f.response_member = :member_check", Friend.class)
                .setParameter("member_check", member)
                .getResultList();
    }

    public Friend findById(Long friendId) {
        return em.find(Friend.class, friendId);
    }

    public Friend findByRequestAndResponseMember(Member request_member, Member response_member) {
        return em.createQuery("select f from Friend f" +
                        " where (f.request_member = :requestMember and f.response_member = :responseMember)" +
                        "AND (f.request_member = :responseMember AND f.response_member = :requestMember)", Friend.class)
                .setParameter("requestMember", request_member)
                .setParameter("responseMember", response_member)
                .getSingleResult();
    }

    // 친구 관계 양방향 쌍을 찾기
    public boolean existsByMembers(Member request_member, Member response_member) {
        Long count = em.createQuery(
                        "SELECT COUNT(f) FROM Friend f " +
                                "WHERE (f.request_member = :requestMember AND f.response_member = :responseMember) " +
                                "OR (f.request_member = :responseMember AND f.response_member = :requestMember)", Long.class)
                .setParameter("requestMember", request_member)
                .setParameter("responseMember", response_member)
                .getSingleResult();
        return count > 0;
    }



    // 테스트 용도로만 사용
    public void flushAndClear() {
        em.flush();
        em.clear();
    }
}
