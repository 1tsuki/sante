package com.astrider.sfc.app.test.app.helper;

import static org.junit.Assert.*;

import org.junit.Test;

import com.astrider.sfc.app.test.app.model.dao.DaoTestBase;
import com.astrider.sfc.src.helper.SanteUtils;
import com.astrider.sfc.src.model.vo.db.RecipeNutAmountsVo;

public class SanteUtilsTest extends DaoTestBase {

    public void 栄養バランスを計算() {
        RecipeNutAmountsVo recipeNut = recipeNutDao.selectById(1);
        System.out.println(SanteUtils.calcTotalBalance(recipeNut));
    }

}
