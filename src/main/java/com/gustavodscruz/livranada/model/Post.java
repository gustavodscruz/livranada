package com.gustavodscruz.livranada.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livra_posts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "is_comment")
    private boolean comment;

    private String title;

    @Column(name = "post_comment")
    private String postContent;

    @OneToOne
    @JoinColumn(name = "post_user")
    private User user;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_post_fk")
    private Post parentPost;




}
