package com.astrider.sfc.src.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.astrider.sfc.app.lib.helper.Mapper;
import com.astrider.sfc.app.lib.model.dao.BaseDao;
import com.astrider.sfc.src.model.vo.db.NutrientVo;

public class NutrientDao extends BaseDao {
	public NutrientDao() {
		super();
	}

	public NutrientDao(Connection con) {
		super(con);
	}

	public ArrayList<NutrientVo> selectAll() {
		ArrayList<NutrientVo> nutrients = new ArrayList<NutrientVo>();
		PreparedStatement pstmt = null;

		try {
			String sql = "SELECT * FROM nutrients";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<NutrientVo> mapper = new Mapper<NutrientVo>();
				NutrientVo nutrient = mapper.fromResultSet(rs);
				nutrients.add(nutrient);
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
		return nutrients;
	}

	public NutrientVo selectById(int id) {
		NutrientVo nutrient = null;
		PreparedStatement pstmt = null;

		try {
			String sql = "SELECT * FROM nutrients WHERE nutrient_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<NutrientVo> mapper = new Mapper<NutrientVo>();
				nutrient = mapper.fromResultSet(rs);
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
		return nutrient;
	}
}
