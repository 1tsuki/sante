package com.astrider.sfc.src.model.vo.db;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Table;
import com.astrider.sfc.app.annotation.Valid;
import com.astrider.sfc.app.lib.BaseVo;

@Table("nutrients")
public class NutrientVo extends BaseVo {
	private static final long serialVersionUID = 4115159396942982252L;

	@Column(physic = "nutrient_id", logic = "栄養素ID", isPrimaryKey = true)
	@Valid(isNotNull = true, isNotBlank = true, isMin = true, min = 0)
	private int nturientId;
	@Column(physic = "physical_name", logic = "栄養素物理名")
	private String physicalName;
	@Column(physic = "logical_name", logic = "栄養素論理名")
	private String logicalName;
	@Column(physic = "nutrient_color", logic = "色種別")
	private String nutrientColor;
	@Column(physic = "daily_required_amount", logic = "日別摂取推奨量")
	private int dailyRequiredAmount;

	public int getNturientId() {
		return nturientId;
	}

	public void setNturientId(int nturientId) {
		this.nturientId = nturientId;
	}

	public String getPhysicalName() {
		return physicalName;
	}

	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}

	public String getLogicalName() {
		return logicalName;
	}

	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}

	public String getNutrientColor() {
		return nutrientColor;
	}

	public void setNutrientColor(String nutrientColor) {
		this.nutrientColor = nutrientColor;
	}

	public int getDailyRequiredAmount() {
		return dailyRequiredAmount;
	}

	public void setDailyRequiredAmount(int dailyRequiredAmount) {
		this.dailyRequiredAmount = dailyRequiredAmount;
	}

}
