package com.gustavodscruz.livranada.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "livra_authors")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue
    @Column(name = "author_id")
    private Long id;

    private String name;
    private Date birthDate;
    @ManyToMany
    @JoinTable(
            name = "authors_genres",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @Column(name = "stars_review")
    private Long starsReview;

    @OneToOne(optional = true)
    @JoinColumn(name = "author_user_account")
    private User user;

}
