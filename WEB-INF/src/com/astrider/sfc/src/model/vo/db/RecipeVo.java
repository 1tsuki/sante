package com.astrider.sfc.src.model.vo.db;

import com.astrider.sfc.app.lib.helper.annotation.*;
import com.astrider.sfc.app.lib.model.vo.BaseVo;

@Table("recipes")
public class RecipeVo extends BaseVo {
    private static final long serialVersionUID = -5161857920063792063L;
    @Column(physic="recipe_id", logic="レシピID", isPrimaryKey=true)
    @Valid(isNotNull=true, isNotBlank=true, isMaxLength=true, maxLength=8)
    private int recipeId;
    @Column(physic="recipe_name", logic="レシピ名")
    @Valid(isNotNull=true, isNotBlank=true, isMaxLength=true, maxLength=64)
    private String recipeName;
    @Column(physic="description", logic="コメント")
    @Valid(isMaxLength=true, maxLength=512)
    private String description;
    @Column(physic="image_url", logic="画像URL")
    @Valid(isMaxLength=true, maxLength=512)
    private String imageUrl;
    @Column(physic="estimated_time", logic="調理時間")
    @Valid(isMax=true, max=999, isMin=true, min=0)
    private int estimatedTime;
    @Column(physic="is_deleted", logic="削除フラグ")
    private boolean isDeleted;

    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public int getEstimatedTime() {
        return estimatedTime;
    }
    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
