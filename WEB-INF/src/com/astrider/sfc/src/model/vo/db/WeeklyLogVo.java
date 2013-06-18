package com.astrider.sfc.src.model.vo.db;

import java.sql.Date;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Table;
import com.astrider.sfc.app.model.BaseVo;

@Table("weekly_nut_amounts")
public class WeeklyLogVo extends BaseVo {
	private static final long serialVersionUID = -8384025897873070337L;

	@Column(physic = "week_id", logic = "週ID", isPrimaryKey = true)
	private int weekId;
	@Column(physic = "user_id", logic = "ユーザーID")
	private int userId;
	@Column(physic = "total_balance", logic = "栄養バランス")
	private int totalBalance;
	@Column(physic = "milk", logic = "乳製品")
	private int milk = 0;
	@Column(physic = "egg", logic = "卵類")
	private int egg = 0;
	@Column(physic = "meat", logic = "肉類")
	private int meat = 0;
	@Column(physic = "bean", logic = "豆類")
	private int bean = 0;
	@Column(physic = "vegetable", logic = "野菜")
	private int vegetable = 0;
	@Column(physic = "potato", logic = "イモ類")
	private int potato = 0;
	@Column(physic = "fruit", logic = "果物")
	private int fruit = 0;
	@Column(physic = "mineral", logic = "きのこ・海藻類")
	private int mineral = 0;
	@Column(physic = "crop", logic = "穀物")
	private int crop = 0;
	@Column(physic = "fat", logic = "油脂")
	private int fat = 0;
	@Column(physic = "suguar", logic = "糖分")
	private int suguar = 0;
	@Column(physic = "first_date", logic = "食事時刻")
	private Date firstDate;
	@Column(physic = "updated_at", logic = "最終更新時刻")
	private Date updatedAt;

	public int getWeekId() {
		return weekId;
	}

	public void setWeekId(int weekId) {
		this.weekId = weekId;
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

	public Date getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
