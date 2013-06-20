package com.astrider.sfc.src.model.vo.form;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.lib.BaseVo;

public class SearchQueryVo extends BaseVo {
	private static final long serialVersionUID = 5770282629300515850L;

	@Column(physic = "material_name", logic = "素材名")
	private String materialName;
	@Column(physic = "nutrient_id", logic = "素材ID")
	private int nutrientId;

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public int getNutrientId() {
		return nutrientId;
	}

	public void setNutrientId(int nutrientId) {
		this.nutrientId = nutrientId;
	}
}
