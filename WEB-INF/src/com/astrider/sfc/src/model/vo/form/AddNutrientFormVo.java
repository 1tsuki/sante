package com.astrider.sfc.src.model.vo.form;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Valid;
import com.astrider.sfc.app.lib.BaseVo;

public class AddNutrientFormVo extends BaseVo {
	private static final long serialVersionUID = 7517367360398373034L;
	
	@Column(physic = "nutrient_id", logic = "栄養素ID")
	@Valid(isNotNull = true, isNotBlank = true, isMin = true, isMax = true, min = 1, max = 11)
	private int nutrientId;
	@Column(physic = "amount", logic = "総量")
	@Valid(isNotNull = true, isNotBlank = true)
	private int amount;
	public int getNutrientId() {
		return nutrientId;
	}
	public void setNutrientId(int nutrientId) {
		this.nutrientId = nutrientId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
