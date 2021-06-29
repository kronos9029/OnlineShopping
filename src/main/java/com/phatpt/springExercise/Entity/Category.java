package com.phatpt.springExercise.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_category")
public class Category implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cateId;
    private String cateName;
    private String cateDescription;
    
    public Category() {
        super();
    }

    public Category(String cateName, String cateDescription) {
        this.cateName = cateName;
        this.cateDescription = cateDescription;
    }



    public long getCategoryID() {
        return cateId;
    }

    public void setCategoryID(long cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateDescription() {
        return cateDescription;
    }

    public void setCateDescription(String cateDescription) {
        this.cateDescription = cateDescription;
    }

    

    



}
