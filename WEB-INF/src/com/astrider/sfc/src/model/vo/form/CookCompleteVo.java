package com.astrider.sfc.src.model.vo.form;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Valid;
import com.astrider.sfc.app.model.BaseVo;

public class CookCompleteVo extends BaseVo {
	private static final long serialVersionUID = 8642578650634734759L;

	@Column(physic = "recipe_id", logic = "レシピID")
	@Valid(isNotNull = true, isNotBlank = true, isMin = true, min = 0)
	private int recipeId;

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

}
