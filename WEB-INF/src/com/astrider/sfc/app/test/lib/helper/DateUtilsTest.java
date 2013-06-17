package com.astrider.sfc.app.test.lib.helper;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import com.astrider.sfc.app.lib.helper.DateUtils;

public class DateUtilsTest {

    @Test
    public void 正常系テスト() {
        int dayPassed = 3;
        Calendar comparer = Calendar.getInstance();
        Calendar comparee = Calendar.getInstance();
        comparee.add(Calendar.DATE, dayPassed);

        assertTrue(DateUtils.getDayPassed(comparer, comparee) == dayPassed);
    }

    @Test
    public void 古い日付を左記に与えても正しく取得() {
        int dayPassed = 2;
        Calendar comparer = Calendar.getInstance();
        Calendar comparee = Calendar.getInstance();
        comparee.add(Calendar.DATE, dayPassed);

        assertTrue(DateUtils.getDayPassed(comparee, comparer) == dayPassed);
    }
}
