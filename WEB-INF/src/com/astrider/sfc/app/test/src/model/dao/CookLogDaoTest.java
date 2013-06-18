package com.astrider.sfc.app.test.src.model.dao;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import com.astrider.sfc.src.model.vo.db.CookLogVo;

public class CookLogDaoTest extends DaoTestBase {
    @Test
    public void insert正常系() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = new Date(cal.getTimeInMillis());

        CookLogVo cookLog = new CookLogVo();
        cookLog.setRecipeId(1);
        cookLog.setUserId(1);
        cookLog.setCookedAt(date);
        assertTrue(cookLogDao.insert(cookLog));
    }

    @Test
    public void insert時に自動でcookedAtを挿入() {
        CookLogVo cookLog = new CookLogVo();
        cookLog.setRecipeId(1);
        cookLog.setUserId(1);
        assertTrue(cookLogDao.insert(cookLog));
    }

    @Test
    public void selectCookedAtYesterday複数件取得() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = new Date(cal.getTimeInMillis());

        CookLogVo cookLog1 = new CookLogVo();
        cookLog1.setRecipeId(1);
        cookLog1.setUserId(1);
        cookLog1.setCookedAt(date);
        assertTrue(cookLogDao.insert(cookLog1));

        CookLogVo cookLog2 = new CookLogVo();
        cookLog2.setRecipeId(1);
        cookLog2.setUserId(1);
        cookLog2.setCookedAt(date);
        assertTrue(cookLogDao.insert(cookLog2));

        ArrayList<CookLogVo> logs = cookLogDao.selectCookedAtYesterday(1);
        assertTrue(logs.size() == 3);
    }
}
