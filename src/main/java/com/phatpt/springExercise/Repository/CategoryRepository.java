package com.phatpt.springExercise.repository;

import com.phatpt.springExercise.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    @Query("FROM Category WHERE cate_id = ?1")
    Category findCateById(long id);
}
