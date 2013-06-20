package com.astrider.sfc.src.model.vo.db;

import java.sql.Date;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Table;
import com.astrider.sfc.app.lib.BaseVo;

@Table("meal_nut_amounts")
public class MealLogVo extends BaseVo {
	private static final long serialVersionUID = -8384025897873070337L;

	@Column(physic = "meal_id", logic = "食事ID", isPrimaryKey = true)
	private int mealId;
	@Column(physic = "user_id", logic = "ユーザーID")
	private int userId;
	@Column(physic = "total_balance", logic = "栄養バランス")
	private int totalBalance;
	@Column(physic = "milk", logic = "乳製品")
	private int milk;
	@Column(physic = "egg", logic = "卵類")
	private int egg;
	@Column(physic = "meat", logic = "肉類")
	private int meat;
	@Column(physic = "bean", logic = "豆類")
	private int bean;
	@Column(physic = "vegetable", logic = "野菜")
	private int vegetable;
	@Column(physic = "potato", logic = "イモ類")
	private int potato;
	@Column(physic = "fruit", logic = "果物")
	private int fruit;
	@Column(physic = "mineral", logic = "きのこ・海藻類")
	private int mineral;
	@Column(physic = "crop", logic = "穀物")
	private int crop;
	@Column(physic = "fat", logic = "油脂")
	private int fat;
	@Column(physic = "suguar", logic = "糖分")
	private int suguar;
	@Column(physic = "meal_at", logic = "食事時刻")
	private Date mealAt;
	@Column(physic = "updated_at", logic = "最終更新時刻")
	private Date updatedAt;

	public int getMealId() {
		return mealId;
	}

	public void setMealId(int mealId) {
		this.mealId = mealId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(int totalBalance) {
		this.totalBalance = totalBalance;
	}

	public int getMilk() {
		return milk;
	}

	public void setMilk(int milk) {
		this.milk = milk;
	}

	public int getEgg() {
		return egg;
	}

	public void setEgg(int egg) {
		this.egg = egg;
	}

	public int getMeat() {
		return meat;
	}

	public void setMeat(int meat) {
		this.meat = meat;
	}

	public int getBean() {
		return bean;
	}

	public void setBean(int bean) {
		this.bean = bean;
	}

	public int getVegetable() {
		return vegetable;
	}

	public void setVegetable(int vegetable) {
		this.vegetable = vegetable;
	}

	public int getPotato() {
		return potato;
	}

	public void setPotato(int potato) {
		this.potato = potato;
	}

	public int getFruit() {
		return fruit;
	}

	public void setFruit(int fruit) {
		this.fruit = fruit;
	}

	public int getMineral() {
		return mineral;
	}

	public void setMineral(int mineral) {
		this.mineral = mineral;
	}

	public int getCrop() {
		return crop;
	}

	public void setCrop(int crop) {
		this.crop = crop;
	}

	public int getFat() {
		return fat;
	}

	public void setFat(int fat) {
		this.fat = fat;
	}

	public int getSuguar() {
		return suguar;
	}

	public void setSuguar(int suguar) {
		this.suguar = suguar;
	}

	public Date getMealAt() {
		return mealAt;
	}

	public void setMealAt(Date mealAt) {
		this.mealAt = mealAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
