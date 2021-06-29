package com.phatpt.springExercise.Repository;

import com.phatpt.springExercise.Entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface categoryRepository extends JpaRepository<Category, Long>{
    
}
