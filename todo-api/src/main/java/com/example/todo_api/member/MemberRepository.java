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

    public Member findByLoginIdAndPassword(String login_id, String login_password) {
        try {
            return em.createQuery(
                            "SELECT m FROM Member m WHERE m.login_id = :login_id AND m.login_password = :login_password", Member.class)
                    .setParameter("login_id", login_id)
                    .setParameter("login_password", login_password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean existsByLoginId(String newLoginId) {
            Long count = em.createQuery(
                            "SELECT COUNT(m) FROM Member m WHERE m.login_id = :loginId", Long.class)
                    .setParameter("loginId", newLoginId)
                    .getSingleResult();

            return count > 0;
        }


    // 수정

    // 삭제
    public void deleteById(Long memberId) {
        Member member = findById(memberId);
        em.remove(member);
    }

    public void flushAndClear() {
        em.flush();
        em.clear();
    }

}
