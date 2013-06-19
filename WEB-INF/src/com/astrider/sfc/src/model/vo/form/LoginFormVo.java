package com.astrider.sfc.src.model.vo.form;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Valid;
import com.astrider.sfc.app.model.BaseVo;


public class LoginFormVo extends BaseVo {
    private static final long serialVersionUID = 3395655886710155573L;
    @Column(physic="email", logic="メールアドレス")
    @Valid(isNotNull = true, isNotBlank = true, isMaxLength = true, maxLength = 128, isEmail = true)
    private String email;
    @Column(physic="password", logic="パスワード")
    @Valid(isNotNull = true, isNotBlank = true)
    private String password;

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
}
