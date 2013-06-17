package com.astrider.sfc.test.app.helper;

import static org.junit.Assert.*;

import org.junit.Test;

import com.astrider.sfc.app.helper.SanteUtils;
import com.astrider.sfc.app.model.vo.db.RecipeNutAmountsVo;
import com.astrider.sfc.test.app.model.dao.DaoTestBase;

public class SanteUtilsTest extends DaoTestBase {

    public void 栄養バランスを計算() {
        RecipeNutAmountsVo recipeNut = recipeNutDao.selectById(1);
        System.out.println(SanteUtils.calcTotalBalance(recipeNut));
    }

}
