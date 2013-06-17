package com.astrider.sfc.app.model.vo.db;

import com.astrider.sfc.lib.helper.annotation.*;
import com.astrider.sfc.lib.model.vo.BaseVo;

@Table("nutrients")
public class NutrientVo extends BaseVo {
    private static final long serialVersionUID = 4115159396942982252L;

    @Column(physic="nutrient_id", logic="栄養素ID", isPrimaryKey=true)
    @Valid(isNotNull=true, isNotBlank=true, isMin=true, min=0)
    private int nturientId;
    @Column(physic="nutrient_name", logic="栄養素名")
    private String nutrientName;
    @Column(physic="nutrient_color", logic="色種別")
    private String nutrientColor;
    @Column(physic="daily_required_amount", logic="日別摂取推奨量")
    private int dailyRequiredAmount;
    public int getNturientId() {
        return nturientId;
    }
    public void setNturientId(int nturientId) {
        this.nturientId = nturientId;
    }
    public String getNutrientName() {
        return nutrientName;
    }
    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
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
