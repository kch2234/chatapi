package com.react.chat.repository;

import com.react.chat.domain.member.Member;
import com.react.chat.domain.member.ProfileImage;
import com.react.chat.dto.ProfileImageDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

  @Query("select m from Member m where m.email = :email")
  Member getMemberByEmail(@Param("email") String email);

  Optional<Member> findById(Long id);
}
