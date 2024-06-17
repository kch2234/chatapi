package com.react.chat.repository;

import com.react.chat.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  @Query("select m from Member m where m.email = :email")
  Member getMemberByEmail(@Param("email") String email);

//  Optional<Member> findByEmail(String email);

  @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.email = :email")
  boolean existsByEmail(@Param("email") String email);

  @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.nickname = :nickname")
  boolean existsByNickname(@Param("nickname") String nickname);

  @Query("SELECT m FROM Member m WHERE m.disabled = false")
  Page<Member> findAllActiveMembers(Pageable pageable);

/*  @Modifying(clearAutomatically = true)
  @Query("update Member m set m.delFlag = :delFlag where m.id = :id")
  void updateDelFlag(@Param("pno") Long pno, @Param("delFlag") boolean delFlag);*/

  @Query("select m, mi from Member m left join m.imageList mi where mi.ord=0")
  Page<Object[]> selectList(Pageable pageable);
}
