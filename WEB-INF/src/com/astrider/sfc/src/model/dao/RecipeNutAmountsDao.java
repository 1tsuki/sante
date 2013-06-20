package com.astrider.sfc.src.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.astrider.sfc.app.lib.BaseDao;
import com.astrider.sfc.app.lib.Mapper;
import com.astrider.sfc.src.model.vo.db.RecipeNutAmountsVo;

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
