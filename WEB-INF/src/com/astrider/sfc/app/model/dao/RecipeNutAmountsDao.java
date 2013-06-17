package com.astrider.sfc.app.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.astrider.sfc.app.model.vo.db.RecipeNutAmountsVo;
import com.astrider.sfc.lib.helper.Mapper;
import com.astrider.sfc.lib.model.dao.BaseDao;

public class RecipeNutAmountsDao extends BaseDao {
    public RecipeNutAmountsDao() {
        super();
    }

    public RecipeNutAmountsDao(Connection con) {
        super(con);
    }

    public RecipeNutAmountsVo selectById(int recipeId) {
        RecipeNutAmountsVo recipeNut = null;
        PreparedStatement pstmt = null;

        try {
            String sql = "SELECT * FROM recipe_nut_amounts WHERE recipe_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, recipeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Mapper<RecipeNutAmountsVo> mapper = new Mapper<RecipeNutAmountsVo>();
                recipeNut = mapper.fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return recipeNut;
    }

}
