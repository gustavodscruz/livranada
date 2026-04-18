package com.gustavodscruz.livranada.model;

import com.gustavodscruz.livranada.service.validations.annotations.CountryValidAge;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

@Entity
@Table(name = "livra_users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
//validations
@CountryValidAge
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty(message = "User must have a username.")
    private String username;

    @Email(message = "User must have a valid email.")
    private String email;

    @Transient
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", message = "Password must contain at least one letter and one number.")
    private String password;

    @Column(name = "password")
    private String passwordHash;


    @Column(name = "comments_number")
    private Long numberOfComments;

    @Column(name = "birth_date")
    @PastOrPresent(message = "Birth date must be in the past or present.")
    private LocalDate birthDate;

    @Column(name = "stars_review")
    private Long starsReview;

    @Column(name = "is_author")
    private boolean author;

    @Column(length = 2)
    @Pattern(regexp = "^[A-Z]{2}$", message = "Country must be an ISO 3166-1 alpha-2 code (2 uppercase letters).")
    private String country;
}
