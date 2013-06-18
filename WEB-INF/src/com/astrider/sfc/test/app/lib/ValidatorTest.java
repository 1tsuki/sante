package com.astrider.sfc.test.app.lib;

import static org.junit.Assert.*;

import org.junit.Test;

import com.astrider.sfc.app.lib.Validator;
import com.astrider.sfc.src.model.vo.db.UserVo;

public class ValidatorTest {
    @Test
    public void 正常なリクエスト() {
        UserVo vo = new UserVo();
        vo.setUserId(1);
        vo.setUserName("asdf");
        vo.setEmail("sample@hogemoge.com");
        vo.setAuthToken("asdfadsf");
        vo.setEmailToken("1234567890123456");
        vo.setMale(true);
        Validator<UserVo> validator = new Validator<UserVo>(vo);
        assertTrue(validator.valid());
    }

    @Test
    public void notNull違反() {
        UserVo vo = new UserVo();
        vo.setUserId(1);
        vo.setEmail("sample@hogemoge.com");
        vo.setAuthToken("asdfadsf");
        vo.setEmailToken("1234567890123456");
        vo.setMale(true);
        Validator<UserVo> validator = new Validator<UserVo>(vo);
        assertFalse(validator.valid());
        for (String message : validator.getFlashMessage().getMessages()) {
            System.out.println(message);
        }
    }

    @Test
    public void notBlank違反() {
        UserVo vo = new UserVo();
        vo.setUserId(1);
        vo.setUserName("");
        vo.setEmail("sample@hogemoge.com");
        vo.setAuthToken("asdfadsf");
        vo.setEmailToken("asdfasdfas");
        vo.setMale(true);
        Validator<UserVo> validator = new Validator<UserVo>(vo);
        assertFalse(validator.valid());
        for (String message : validator.getFlashMessage().getMessages()) {
            System.out.println(message);
        }
    }

    @Test
    public void isLength違反() {
        UserVo vo = new UserVo();
        vo.setUserId(1);
        vo.setUserName("asdf");
        vo.setEmail("sample@hogemoge.com");
        vo.setAuthToken("asdfadsf");
        vo.setEmailToken("12345678901234567");
        vo.setMale(true);
        Validator<UserVo> validator = new Validator<UserVo>(vo);
        assertFalse(validator.valid());
        for (String message : validator.getFlashMessage().getMessages()) {
            System.out.println(message);
        }
    }
}
