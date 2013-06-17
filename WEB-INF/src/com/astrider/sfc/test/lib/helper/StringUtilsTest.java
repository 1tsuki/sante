package com.astrider.sfc.test.lib.helper;

import static org.junit.Assert.*;

import org.junit.Test;

import com.astrider.sfc.lib.helper.StringUtils;

public class StringUtilsTest {

    @Test
    public void isNotEmpty正常系() {
        assertTrue(StringUtils.isNotEmpty("asdf"));
    }

    @Test
    public void isNotEmpty異常系null() {
        assertFalse(StringUtils.isNotEmpty(null));
    }


    @Test
    public void isNotEmpty異常系blank() {
        assertFalse(StringUtils.isNotEmpty(""));
    }

    @Test
    public void isEmpty正常系() {
        assertTrue(StringUtils.isEmpty(""));
    }

    @Test
    public void isEmpty異常系null() {
        assertFalse(StringUtils.isEmpty(null));
    }


    @Test
    public void isEmpty異常系notBlank() {
        assertFalse(StringUtils.isEmpty("asdf"));
    }

    @Test
    public void getUniqueString() {
        assertTrue(StringUtils.isNotEmpty(StringUtils.getUniqueString()));
    }

    @Test
    public void getHash() {
        assertTrue(StringUtils.getHash("asdf").length() == 128);
    }
}
