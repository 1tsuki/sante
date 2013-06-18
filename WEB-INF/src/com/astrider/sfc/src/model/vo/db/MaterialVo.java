package com.astrider.sfc.src.model.vo.db;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Table;
import com.astrider.sfc.app.annotation.Valid;
import com.astrider.sfc.app.lib.StringUtils;
import com.astrider.sfc.app.model.BaseVo;

@Table("materials")
public class MaterialVo extends BaseVo {
	private static final long serialVersionUID = -4318950366299339701L;

	@Column(physic = "material_name", logic = "素材名", isPrimaryKey = true)
	@Valid(isNotNull = true, isNotBlank = true)
	private String materialName = " ";
	@Column(physic = "gram_per_quantity", logic = "単位あたりグラム数")
	@Valid(isNotNull = true, isMin = true, min = 0)
	private int gramPerQuantity;
	@Column(physic = "prefix", logic = "接頭辞", isPrimaryKey = true)
	@Valid(isNotNull = true)
	private String prefix = " ";
	@Column(physic = "postfix", logic = "接尾辞", isPrimaryKey = true)
	@Valid(isNotNull = true)
	private String postfix = " ";
	@Column(physic = "nutrient_id", logic = "栄養素ID")
	private int nutrientId;

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
		if (StringUtils.isEmpty(prefix)) {
			prefix = " ";
		}
		this.prefix = prefix;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		if (StringUtils.isEmpty(postfix)) {
			postfix = " ";
		}
		this.postfix = postfix;
	}

	public int getNutrientId() {
		return nutrientId;
	}

	public void setNutrientId(int nutrientId) {
		this.nutrientId = nutrientId;
	}
}
