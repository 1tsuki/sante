package com.astrider.sfc.src.model.vo.db;

import com.astrider.sfc.app.lib.helper.annotation.Column;
import com.astrider.sfc.app.lib.helper.annotation.Table;
import com.astrider.sfc.app.lib.helper.annotation.Valid;
import com.astrider.sfc.app.lib.model.vo.BaseVo;

@Table("recipe_material_quantities")
public class MaterialQuantityVo extends BaseVo {
	private static final long serialVersionUID = -5659493914207800466L;

	@Column(physic = "recipe_id", logic = "レシピID")
	@Valid(isNotNull = true, isMin = true, min = 0)
	private int recipeId;
	@Column(physic = "material_name", logic = "素材名")
	@Valid(isNotNull = true, isNotBlank = true)
	private String materialName;
	@Column(physic = "gram_per_quantity", logic = "単位あたりグラム数")
	@Valid(isNotNull = true, isMin = true, min = 0)
	private int gramPerQuantity;
	@Column(physic = "prefix", logic = "接頭辞")
	private String prefix;
	@Column(physic = "postfix", logic = "接尾辞")
	private String postfix;
	@Column(physic = "nutrient_id", logic = "栄養素ID")
	private int nutrientId;
	@Column(physic = "quantity", logic = "必要量")
	@Valid(isNotNull = true, isMin = true, min = 0)
	private float quantity;

	public MaterialQuantityVo() {
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public int getGramPerQuantity() {
		return gramPerQuantity;
	}

	public void setGramPerQuantity(int gramPerQuantity) {
		this.gramPerQuantity = gramPerQuantity;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public int getNutrientId() {
		return nutrientId;
	}

	public void setNutrientId(int nutrientId) {
		this.nutrientId = nutrientId;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
}
