package com.phatpt.springExercise.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.phatpt.springExercise.Entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    @Query("FROM Product WHERE cate_id = ?1")
    List<Product> findAllProductsByCateId(long cateId);

    @Query("FROM Product WHERE product_id = ?1")
    Product findProductById(long productId);

    @Transactional
    @Modifying
    @Query("UPDATE Product SET quantity = ?1 WHERE product_id = ?2")
    int updateQuantity(int newQuantity, long productId);
}
