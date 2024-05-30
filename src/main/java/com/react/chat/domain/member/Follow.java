package com.react.chat.domain.member;


import com.react.chat.domain.baseEntity.BaseEntityCreatedDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Follow extends BaseEntityCreatedDate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 팔로우 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followerId")
    private Member follower; // 나를 팔로우 하는 유저 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followId")
    private Member follow; // 내가 팔로우 하는 대상 ID

    public Follow(Member follower, Member follow) {
        this.follower = follower; // 나를 팔로우 하는 유저
        this.follow = follow; // 내가 팔로우 하는 대상
    }
}
