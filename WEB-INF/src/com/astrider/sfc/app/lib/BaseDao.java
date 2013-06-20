package com.astrider.sfc.app.lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import static com.astrider.sfc.ApplicationContext.*;


/**
 * Dao基底クラス.
 * 
 * @author astrider
 *         <p>
 *         DBコネクション機能及びinsert,update機能をデフォルトで持つ。<br>
 *         利用後は必ずclose()を実行する必要がある<br>
 *         </p>
 */
public class BaseDao {
	private Context context = null;
	private DataSource ds = null;
	protected Connection con = null;

	public BaseDao() {
		try {
			context = new InitialContext();
			ds = (DataSource) context.lookup(DB_LOCALNAME);
			con = ds.getConnection();
			con.setAutoCommit(false);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * dbUnit用.
	 * 
	 * @param con
	 */
	public BaseDao(Connection con) {
		this.con = con;
	}

	/**
	 * insert(自動コミット).
	 * 
	 * @param vo
	 * @return 挿入成否
	 */
	public <T extends BaseVo> boolean insert(T vo) {
		return insert(vo, true);
	}

	/**
	 * insert.
	 * 
	 * @param vo
	 * @param autoCommit
	 * @return 挿入成否
	 */
	public <T extends BaseVo> boolean insert(T vo, boolean autoCommit) {
		// validationに失敗したら挿入しない
		Validator<T> validator = new Validator<T>(vo);
		if (!validator.valid()) {
			return false;
		}

		boolean succeed = false;
		PreparedStatement pstmt = null;
		try {
			QueryBuilder<T> qb = new QueryBuilder<T>(vo, con);
			pstmt = qb.getInsertPstmt();
			int resultCount = pstmt.executeUpdate();
			if (resultCount == 1) {
				succeed = true;
			}
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

		if (autoCommit && succeed) {
			commit();
		}
		if (autoCommit && !succeed) {
			rollback();
		}
		return succeed;
	}

	/**
	 * update(自動コミット).
	 * 
	 * @param vo
	 * @return 更新成否
	 */
	public <T extends BaseVo> boolean update(T vo) {
		return update(vo, true);
	}

	/**
	 * update.
	 * 
	 * @param vo
	 * @param autoCommit
	 * @return 更新成否
	 */
	public <T extends BaseVo> boolean update(T vo, boolean autoCommit) {
		// validationに失敗したら更新しない
		Validator<T> validator = new Validator<T>(vo);
		if (!validator.valid()) {
			return false;
		}

		boolean succeed = false;
		PreparedStatement pstmt = null;

		try {
			QueryBuilder<T> qb = new QueryBuilder<T>(vo, con);
			pstmt = qb.getUpdatePstmt();
			int resultCount = pstmt.executeUpdate();
			if (resultCount == 1) {
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

		if (autoCommit && succeed) {
			commit();
		}
		if (autoCommit && !succeed) {
			rollback();
		}
		return succeed;
	}

	/**
	 * コミット.
	 */
	public void commit() {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ロールバック.
	 */
	public void rollback() {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * DB切断.
	 */
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
