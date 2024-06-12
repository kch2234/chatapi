package com.react.chat.repository;

import com.react.chat.domain.member.UserLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLanguageRepository extends JpaRepository<UserLanguage, Long> {
}
