package com.astrider.sfc.app.test.lib.helper;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.astrider.sfc.app.lib.helper.AuthUtils;

public class AuthUtilsTest {
    @Test
    public void 暗号化後138文字の16進数半角英数字が出力されることを確認() {
        String password = "password";
        String salted = AuthUtils.encrypt(password);
        Pattern pattern = Pattern.compile("[0-9a-f]{138}");
        Matcher matcher = pattern.matcher(salted);
        assertTrue(matcher.matches());
    }

    @Test
    public void 認証テスト() {
        String password = "password";
        String salted = AuthUtils.encrypt(password);
        assertTrue(AuthUtils.verify(password, salted));
    }

    @Test
    public void 不正認証テスト() {
        String password = "password";
        String salted = AuthUtils.encrypt(password);
        assertFalse(AuthUtils.verify("invalid", salted));
    }

}
