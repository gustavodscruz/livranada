package com.gustavodscruz.livranada.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livra_books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long id;

    private String name;
    private String description;
    @ManyToOne
    private Author author;

}
