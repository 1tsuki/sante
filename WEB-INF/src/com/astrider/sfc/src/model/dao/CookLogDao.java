package com.astrider.sfc.src.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.astrider.sfc.app.lib.Mapper;
import com.astrider.sfc.app.model.BaseDao;
import com.astrider.sfc.src.model.vo.db.CookLogVo;

public class CookLogDao extends BaseDao {
	public CookLogDao() {
		super();
	}

	public CookLogDao(Connection con) {
		super(con);
	}

	public ArrayList<CookLogVo> selectCookedAtYesterday(int userId) {
		ArrayList<CookLogVo> cookLogs = new ArrayList<CookLogVo>();
		PreparedStatement pstmt = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM cook_logs WHERE user_id = ? ");
			sb.append("AND cooked_at BETWEEN TRUNC(SYSDATE - 1) AND TRUNC(SYSDATE) - 1/86400");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<CookLogVo> mapper = new Mapper<CookLogVo>();
				CookLogVo cookLog = mapper.fromResultSet(rs);
				cookLogs.add(cookLog);
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
		return cookLogs;
	}

	public ArrayList<CookLogVo> selectCookedAtToday(int userId) {
		ArrayList<CookLogVo> cookLogs = new ArrayList<CookLogVo>();
		PreparedStatement pstmt = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM cook_logs WHERE user_id = ? ");
			sb.append("AND cooked_at BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE + 1) - 1/86400");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<CookLogVo> mapper = new Mapper<CookLogVo>();
				CookLogVo cookLog = mapper.fromResultSet(rs);
				cookLogs.add(cookLog);
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
		return cookLogs;
	}

	public int countCookedAtYesterday(int userId) {
		int count = 0;
		PreparedStatement pstmt = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT COUNT(user_id) FROM cook_logs WHERE user_id = ? ");
			sb.append("AND cooked_at BETWEEN TRUNC(SYSDATE - 1) AND TRUNC(SYSDATE) - 1/86400");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

	public int countCookedAtToday(int userId) {
		int count = 0;
		PreparedStatement pstmt = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT COUNT(user_id) FROM cook_logs WHERE user_id = ? ");
			sb.append("AND cooked_at BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE + 1) - 1/86400");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}
}
