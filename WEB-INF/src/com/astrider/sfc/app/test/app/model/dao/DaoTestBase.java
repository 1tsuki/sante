package com.astrider.sfc.app.test.app.model.dao;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;

import com.astrider.sfc.app.test.lib.TestDataManager;
import com.astrider.sfc.src.model.dao.CookLogDao;
import com.astrider.sfc.src.model.dao.MealLogDao;
import com.astrider.sfc.src.model.dao.NutrientDao;
import com.astrider.sfc.src.model.dao.RecipeDao;
import com.astrider.sfc.src.model.dao.RecipeNutAmountsDao;
import com.astrider.sfc.src.model.dao.UserDao;
import com.astrider.sfc.src.model.dao.UserStatsDao;
import com.astrider.sfc.src.model.dao.WeeklyLogDao;

public class DaoTestBase {
    protected CookLogDao cookLogDao;
    protected MealLogDao mealLogDao;
    protected NutrientDao nutrientDao;
    protected RecipeDao recipeDao;
    protected RecipeNutAmountsDao recipeNutDao;
    protected UserDao userDao;
    protected UserStatsDao userStatsDao;
    protected WeeklyLogDao weeklyLogDao;
    private static final String TEST_DATAFILE = "sante.xls";

    private TestDataManager testDataManager;

    @Before
    public void before() throws Exception {
        testDataManager = new TestDataManager(
                TestDataManager.getWebInfPath(),
                "oracle.jdbc.driver.OracleDriver",
                "jdbc:oracle:thin:@localhost:49158:xe",
                "sante",
                "password",
                "sante");

        testDataManager.loadTestDataInXLS(TEST_DATAFILE);
        Connection con = testDataManager.getConnection();
        cookLogDao = new CookLogDao(con);
        mealLogDao = new MealLogDao(con);
        nutrientDao = new NutrientDao(con);
        recipeDao = new RecipeDao(con);
        recipeNutDao = new RecipeNutAmountsDao(con);
        userDao = new UserDao(con);
        userStatsDao = new UserStatsDao(con);
        weeklyLogDao = new WeeklyLogDao(con);
    }

    @After
    public void after() throws Exception {
        if (cookLogDao != null) {
            cookLogDao.close();
        }
        if (mealLogDao != null) {
            mealLogDao.close();
        }
        if (nutrientDao != null) {
            nutrientDao.close();
        }
        if (recipeDao != null) {
            recipeDao.close();
        }
        if (recipeNutDao != null) {
            recipeNutDao.close();
        }
        if (userDao != null) {
            userDao.close();
        }
        if (userStatsDao != null) {
            userStatsDao.close();
        }
        if (weeklyLogDao != null) {
            weeklyLogDao.close();
        }

//        testDataManager.restoreTestDataInXLS(TEST_DATAFILE);
    }
}
