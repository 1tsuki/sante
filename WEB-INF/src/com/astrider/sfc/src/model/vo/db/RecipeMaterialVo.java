package com.astrider.sfc.src.model.vo.db;

import com.astrider.sfc.app.lib.helper.annotation.Column;
import com.astrider.sfc.app.lib.helper.annotation.Table;
import com.astrider.sfc.app.lib.helper.annotation.Valid;
import com.astrider.sfc.app.lib.model.vo.BaseVo;

@Table("recipe_materials")
public class RecipeMaterialVo extends BaseVo {
    private static final long serialVersionUID = 5572762567214836343L;
    @Column(physic="recipe_id", logic="レシピID", isPrimaryKey=true)
    @Valid(isNotNull=true, isMin=true, min=0)
    private int recipeId;
    @Column(physic="material_id", logic="素材ID")
    @Valid(isNotNull=true, isMin=true, min=0)
    private int materialId;
    @Column(physic="quantity", logic="必要量")
    @Valid(isNotNull=true, isMin=true, min=0)
    private float quantity;
    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    public int getMaterialId() {
        return materialId;
    }
    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
    public float getQuantity() {
        return quantity;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
