package com.gustavodscruz.livranada.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "livra_communities")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "community_id")
    private Long id;

    private String name;

    @ManyToOne
    private User createdBy;

    private String description;

    private String iconImage;
    private String backgroundImage;

    @ManyToMany
    @JoinTable(
            name = "communities_musics",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "music_id")
    )
    private Set<Music> musics;
}
