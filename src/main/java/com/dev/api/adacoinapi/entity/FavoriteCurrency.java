package com.dev.api.adacoinapi.entity;

import com.dev.api.adacoinapi.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class FavoriteCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_code")
    private String currencyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
