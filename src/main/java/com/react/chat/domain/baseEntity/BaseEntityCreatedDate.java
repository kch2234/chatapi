package com.react.chat.domain.baseEntity;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

public class BaseEntityCreatedDate {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;
}
