package com.astrider.sfc.src.model.vo.db;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Table;
import com.astrider.sfc.app.annotation.Valid;
import com.astrider.sfc.app.lib.BaseVo;

@Table("recipe_materials")
public class RecipeMaterialVo extends BaseVo {
	private static final long serialVersionUID = 5572762567214836343L;

	@Column(physic = "recipe_id", logic = "レシピID", isPrimaryKey = true)
	@Valid(isNotNull = true, isMin = true, min = 0)
	private int recipeId;
	@Column(physic = "material_name", logic = "素材名", isPrimaryKey = true)
	@Valid(isNotNull = true, isNotBlank = true)
	private String materialName = "";
	@Column(physic = "prefix", logic = "接頭辞", isPrimaryKey = true)
	private String prefix = "";
	@Column(physic = "postfix", logic = "接尾辞", isPrimaryKey = true)
	private String postfix = "";
	@Column(physic = "quantity", logic = "必要量")
	@Valid(isNotNull = true, isMin = true, min = 0)
	private float quantity;

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

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
}
