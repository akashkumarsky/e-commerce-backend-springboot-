package com.sky.ecommerce.repository;

import com.sky.ecommerce.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating , Long> {

    @Query("SELECT r From Rating r where r.productId = :productId")
    public List<Rating> getAllProductsRating(@Param("productId")Long productId);
}
