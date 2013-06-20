package com.astrider.sfc.src.model.vo.form;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Valid;
import com.astrider.sfc.app.lib.BaseVo;

public class ReissueFormVo extends BaseVo {
	private static final long serialVersionUID = -4783791931695707781L;

	@Column(physic = "email", logic = "メールアドレス")
	@Valid(isNotNull = true, isNotBlank = true, isMaxLength = true, maxLength = 128, isEmail = true)
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
