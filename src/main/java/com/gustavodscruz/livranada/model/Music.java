package com.gustavodscruz.livranada.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;

@Entity
@Table(name = "livra_musics")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "music_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    @Column(name = "start_date")
    private OffsetDateTime startDate;
    @Column(name = "end_date")
    private OffsetDateTime endDate;

    @Column(name = "stars_review")
    private Long starsReview;
}
