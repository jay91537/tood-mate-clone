package com.example.todo_api.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.boot.model.source.internal.hbm.XmlElementMetadata;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    // 단건 조회
    public Member findById(Long memberId){
        return em.find(Member.class, memberId);
    }

    // 다건 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member as m", Member.class).getResultList();
    }

    // 수정

    // 삭제
    public void removeByID(Long memberId) {
        Member member = findById(memberId);
        em.remove(member);
    }

    public void flushAndClear() {
        em.flush();
        em.clear();
    }


}
