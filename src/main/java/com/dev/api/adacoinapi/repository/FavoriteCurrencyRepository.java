package com.dev.api.adacoinapi.repository;

import com.dev.api.adacoinapi.entity.FavoriteCurrency;
import org.springframework.data.jpa.repository.JpaRepository;



public interface FavoriteCurrencyRepository extends JpaRepository<FavoriteCurrency, Long> {

}
