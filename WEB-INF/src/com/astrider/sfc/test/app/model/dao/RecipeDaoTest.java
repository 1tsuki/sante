package com.astrider.sfc.test.app.model.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.astrider.sfc.app.model.vo.db.RecipeVo;

public class RecipeDaoTest extends DaoTestBase {
    @Test
    public void selectFromId() {
        RecipeVo recipe = recipeDao.selectByRecipeId(1);
        assertTrue(recipe != null);
    }
}
