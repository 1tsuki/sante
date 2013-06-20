package com.astrider.sfc.src.model.vo.form;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Valid;
import com.astrider.sfc.app.lib.BaseVo;

public class ChangePasswordFormVo extends BaseVo {
	private static final long serialVersionUID = 9018873483406714850L;

	@Column(physic = "current", logic = "現在のパスワード")
	@Valid(isNotNull = true, isNotBlank = true)
	private String currentPassword;
	@Column(physic = "new", logic = "新しいパスワード")
	@Valid(isNotNull = true, isNotBlank = true, isMinLength = true, isRegexp = true, minLength = 8, regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\p{Punct})(?=\\S+$).{8,}$")
	private String newPassword;
	@Column(physic = "new_confirm", logic = "新しいパスワード(確認用)")
	@Valid(isNotNull = true, isNotBlank = true, isMinLength = true, isRegexp = true, minLength = 8, regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\p{Punct})(?=\\S+$).{8,}$")
	private String newPasswordConfirm;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}

	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}
}
