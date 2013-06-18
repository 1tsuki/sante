package com.astrider.sfc.src.model.vo.form;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Valid;
import com.astrider.sfc.app.model.BaseVo;

public class RegisterFormVo extends BaseVo {
	private static final long serialVersionUID = -7366649426401452317L;

	@Column(physic = "username", logic = "ユーザー名")
	@Valid(isNotNull = true, isNotBlank = true, isMaxLength = true, maxLength = 64)
	private String userName;
	@Column(physic = "email", logic = "メールアドレス")
	@Valid(isNotNull = true, isNotBlank = true, isMaxLength = true, maxLength = 128, isEmail = true)
	private String email;
	@Column(physic = "password", logic = "パスワード")
	@Valid(isNotNull = true, isNotBlank = true, isMinLength = true, minLength = 8)
	private String password;
	@Column(physic = "password-confirm", logic = "パスワード(確認用)")
	@Valid(isNotNull = true, isNotBlank = true, isMinLength = true, minLength = 8)
	private String passwordConfirm;
	@Column(physic = "male", logic = "性別")
	private boolean male;
	@Column(physic = "height", logic = "身長")
	@Valid(isMin = true, isMaxLength = true, min = 0, maxLength = 3)
	private int height;
	@Column(physic = "weight", logic = "体重")
	@Valid(isMin = true, isMaxLength = true, min = 0, maxLength = 3)
	private int weight;
	@Column(physic = "age", logic = "年齢")
	private int age;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
