package com.astrider.sfc.src.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.astrider.sfc.app.lib.helper.Mapper;
import com.astrider.sfc.app.lib.model.dao.BaseDao;
import com.astrider.sfc.src.model.vo.db.UserVo;
import com.astrider.sfc.src.model.vo.form.ConfirmEmailVo;

public class UserDao extends BaseDao {
	public UserDao() {
		super();
	}

	public UserDao(Connection con) {
		super(con);
	}

	public UserVo selectByUserId(int userId) {
		UserVo user = null;
		PreparedStatement pstmt = null;

		try {
			String sql = "SELECT * FROM users WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<UserVo> mapper = new Mapper<UserVo>();
				user = mapper.fromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return user;
	}

	public UserVo selectByEmailToken(ConfirmEmailVo vo) {
		UserVo user = null;
		PreparedStatement pstmt = null;

		try {
			String sql = "SELECT * FROM users WHERE email = ? AND email_token = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getToken());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<UserVo> mapper = new Mapper<UserVo>();
				user = mapper.fromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return user;
	}

	public UserVo selectByEmail(String email) {
		UserVo user = null;
		PreparedStatement pstmt = null;

		try {
			String sql = "SELECT * FROM users WHERE email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<UserVo> mapper = new Mapper<UserVo>();
				user = mapper.fromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return user;
	}

	public ArrayList<UserVo> selectAll() {
		ArrayList<UserVo> users = new ArrayList<UserVo>();
		PreparedStatement pstmt = null;

		try {
			String sql = "SELECT * FROM users WHERE is_deleted = 0 AND is_confirmed = 1 AND is_available = 1";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<UserVo> mapper = new Mapper<UserVo>();
				UserVo user = mapper.fromResultSet(rs);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return users;
	}
}
