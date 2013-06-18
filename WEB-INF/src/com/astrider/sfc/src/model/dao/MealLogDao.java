package com.astrider.sfc.src.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.astrider.sfc.app.lib.Mapper;
import com.astrider.sfc.app.model.BaseDao;
import com.astrider.sfc.src.model.vo.db.MealLogVo;

public class MealLogDao extends BaseDao {
	public MealLogDao() {
		super();
	}

	public MealLogDao(Connection con) {
		super(con);
	}

	public boolean insertFromRecipeNutAmounts(int userId, int recipeId) {
		PreparedStatement pstmt = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO meal_nut_amounts");
			sb.append("(user_id, recipe_id, total_balance, milk, egg, meat, bean, vegetable, potato, fruit, mineral, ");
			sb.append("crop, fat, suguar) ");
			sb.append("SELECT ?, recipe_id, total_balance, milk, egg, meat, bean, vegetable, potato, fruit, mineral, ");
			sb.append("crop, fat, suguar FROM recipe_nut_amounts WHERE recipe_id = ?");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, userId);
			pstmt.setInt(2, recipeId);
			int resultCount = pstmt.executeUpdate();
			if (resultCount < 1) {
				return false;
			} else {
				con.commit();
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

		return true;
	}

	public ArrayList<MealLogVo> selectMealAtYesterday(int userId) {
		ArrayList<MealLogVo> meals = new ArrayList<MealLogVo>();
		PreparedStatement pstmt = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM meal_nut_amounts WHERE user_id = ? ");
			sb.append("AND meal_at BETWEEN TRUNC(SYSDATE - 1) AND TRUNC(SYSDATE) - 1/86400");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<MealLogVo> mapper = new Mapper<MealLogVo>();
				MealLogVo meal = mapper.fromResultSet(rs);
				meals.add(meal);
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
		return meals;
	}

	public MealLogVo selectNewestByUserId(int userId) {
		MealLogVo meal = null;
		PreparedStatement pstmt = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM meal_nut_amounts WHERE user_id = ? ");
			sb.append("AND meal_id = (SELECT MAX(meal_id) FROM meal_nut_amounts WHERE user_id = ?)");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, userId);
			pstmt.setInt(2, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<MealLogVo> mapper = new Mapper<MealLogVo>();
				meal = mapper.fromResultSet(rs);
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
		return meal;
	}

	public int countMealOfThisWeek(int userId) {
		int result = -1;
		PreparedStatement pstmt = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT COUNT(meal_id) FROM meal_nut_amounts WHERE user_id = ? AND meal_at BETWEEN ");
			sb.append("(SELECT TRUNC(SYSDATE, 'Day') FROM dual) AND (SELECT TRUNC(SYSDATE+6 , 'Day') FROM dual)");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}

	public MealLogVo selectItemsOfThisWeek(int userId) {
		MealLogVo meals = null;
		PreparedStatement pstmt = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM meal_nut_amounts WHERE user_id = ? AND meal_at BETWEEN ");
			sb.append("(SELECT TRUNC(SYSDATE, 'Day') FROM dual) AND (SELECT TRUNC(SYSDATE+6 , 'Day') FROM dual)");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<MealLogVo> mapper = new Mapper<MealLogVo>();
				meals = mapper.fromResultSet(rs);
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
		return meals;
	}
}
