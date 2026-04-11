package com.gustavodscruz.livranada.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livra_genres")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "genre_id")
    private Long id;
    @Column(name = "genre_name", unique = true)
    private String name;
}
