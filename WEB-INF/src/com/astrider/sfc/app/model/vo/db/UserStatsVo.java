package com.astrider.sfc.app.model.vo.db;

import java.sql.Date;

import com.astrider.sfc.lib.helper.annotation.*;
import com.astrider.sfc.lib.model.vo.BaseVo;

@Table("user_stats")
public class UserStatsVo extends BaseVo {
    private static final long serialVersionUID = -4256938084628780081L;
    @Column(physic="user_id", logic="ユーザーID", isPrimaryKey=true)
    @Valid(isNotNull=true)
    private int userId;
    @Column(physic="nutrients_balance", logic="栄養バランス")
    @Valid(isNotNull=true, isMin=true, isMax=true, min=0, max=100)
    private int nutrientsBalance = 0;
    @Column(physic="total_cooked", logic="調理回数")
    @Valid(isNotNull=true, isMin=true, min=0)
    private int totalCooked = 0;
    @Column(physic="consecutively_cooked", logic="連続調理日数")
    @Valid(isNotNull=true, isMin=true, min=0)
    private int consecutivelyCooked = 0;
    @Column(physic="max_consecutively_cooked", logic="最大連続調理日数")
    @Valid(isNotNull=true, isMin=true, min=0)
    private int maxConsecutivelyCooked = 0;
    @Column(physic="updated_at", logic="最終更新時刻")
    private Date updatedAt;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getNutrientsBalance() {
        return nutrientsBalance;
    }
    public void setNutrientsBalance(int nutrientsBalance) {
        this.nutrientsBalance = nutrientsBalance;
    }
    public int getTotalCooked() {
        return totalCooked;
    }
    public void setTotalCooked(int totalCooked) {
        this.totalCooked = totalCooked;
    }
    public int getConsecutivelyCooked() {
        return consecutivelyCooked;
    }
    public void setConsecutivelyCooked(int consecutivelyCooked) {
        this.consecutivelyCooked = consecutivelyCooked;
    }
    public int getMaxConsecutivelyCooked() {
        return maxConsecutivelyCooked;
    }
    public void setMaxConsecutivelyCooked(int maxConsecutivelyCooked) {
        this.maxConsecutivelyCooked = maxConsecutivelyCooked;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
