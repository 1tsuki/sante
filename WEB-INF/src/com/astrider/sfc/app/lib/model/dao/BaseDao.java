package com.astrider.sfc.app.lib.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.astrider.sfc.app.lib.helper.QueryBuilder;
import com.astrider.sfc.app.lib.model.vo.BaseVo;

/**
 * @author astrider<br>
 *         概要<br>
 *         Dao基底クラス。DBコネクション機能をデフォルトで持つ。<br>
 *         利用後は必ずclose()を実行する必要がある<br>
 * <br>
 *         機能<br>
 *         主要機能<br>
 *         ・insert() extends BaseVoなオブジェクトを一発Insert<br>
 *         ・update() extends BaseVoなオブジェクトを一発Update<br>
 */
public class BaseDao {
	private final String localName = "java:comp/env/jdbc/sante";
	private Context context = null;
	private DataSource ds = null;
	protected Connection con = null;

	public BaseDao() {
		try {
			context = new InitialContext();
			ds = (DataSource) context.lookup(localName);
			con = ds.getConnection();
			con.setAutoCommit(false);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public BaseDao(Connection con) {
		this.con = con;
	}

	public <T extends BaseVo> boolean insertWithoutCommit(T vo) {
		boolean succeed = false;
		PreparedStatement pstmt = null;
		try {
			QueryBuilder<T> qb = new QueryBuilder<T>(vo, con);
			pstmt = qb.getInsertPstmt();
			int resultCount = pstmt.executeUpdate();
			succeed = resultCount == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return succeed;
	}

	public <T extends BaseVo> boolean insert(T vo) {
		boolean succeed = insertWithoutCommit(vo);
		if (succeed) {
			commit();
		} else {
			rollback();
		}
		return succeed;
	}

	public <T extends BaseVo> boolean update(T vo) {
		boolean succeed = false;
		PreparedStatement pstmt = null;

		try {
			QueryBuilder<T> qb = new QueryBuilder<T>(vo, con);
			pstmt = qb.getUpdatePstmt();
			int resultCount = pstmt.executeUpdate();
			if (resultCount == 1) {
				con.commit();
				succeed = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return succeed;
	}

	public void commit() {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void rollback() {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
