package com.astrider.sfc.src.model.vo.db;

import com.astrider.sfc.app.lib.helper.annotation.*;
import com.astrider.sfc.app.lib.model.vo.BaseVo;

@Table("steps")
public class StepVo extends BaseVo {
    private static final long serialVersionUID = 5663299159636041613L;
    @Column(physic="recipe_id", logic="レシピID", isPrimaryKey=true)
    @Valid(isNotNull=true, isMin=true, min=0)
    private int recipeId;
    @Column(physic="step", logic="手順番号", isPrimaryKey=true)
    @Valid(isNotNull=true, isMin=true, min=0)
    private int step;
    @Column(physic="description", logic="手順詳細")
    @Valid(isNotNull=true, isNotBlank=true, isMaxLength=true, maxLength=128)
    private String description;
    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    public int getStep() {
        return step;
    }
    public void setStep(int step) {
        this.step = step;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
