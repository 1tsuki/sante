package com.astrider.sfc.src.model.vo.form;

import com.astrider.sfc.app.lib.helper.annotation.*;
import com.astrider.sfc.app.lib.model.vo.BaseVo;

public class ConfirmEmailVo extends BaseVo {
	private static final long serialVersionUID = -3901324159533290441L;

	@Column(physic = "email", logic = "メールアドレス")
	@Valid(isNotNull = true, isNotBlank = true, isEmail = true)
	private String email;
	@Column(physic = "token", logic = "認証トークン")
	@Valid(isNotNull = true, isNotBlank = true, isLength = true, length = 16)
	private String token;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
