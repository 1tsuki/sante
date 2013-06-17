package com.astrider.sfc.src.model.vo.db;

import java.sql.Date;

import com.astrider.sfc.app.lib.helper.annotation.*;
import com.astrider.sfc.app.lib.model.vo.BaseVo;

@Table("cook_logs")
public class CookLogVo extends BaseVo {
    private static final long serialVersionUID = -8135445196468243780L;
    @Column(physic="cook_log_id", logic="調理履歴ID", isPrimaryKey=true)
    @Valid(isNotNull=true, isNotBlank=true)
    private int cookLogId;
    @Column(physic="user_id", logic="ユーザーID")
    @Valid(isNotNull=true, isNotBlank=true)
    private int userId;
    @Column(physic="recipe_id", logic="レシピID")
    @Valid(isNotNull=true, isNotBlank=true)
    private int recipeId;
    @Column(physic="cooked_at", logic="調理時刻")
    private Date cookedAt;

    public int getCookLogId() {
        return cookLogId;
    }
    public void setCookLogId(int cookLogId) {
        this.cookLogId = cookLogId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    public Date getCookedAt() {
        return cookedAt;
    }
    public void setCookedAt(Date cookedAt) {
        this.cookedAt = cookedAt;
    }
}
