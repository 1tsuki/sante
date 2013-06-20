package com.astrider.sfc.app.lib;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Table;

/**
 * QueryBuilder.
 * 
 * @author astrider
 *         <p>
 *         Insert, Updateに対応した PreparedStatementを生成するクエリビルダー。
 *         SQLの生成、PreparedStatementの生成、値の挿入までを行う。
 *         </p>
 * @param <T>
 *            ValueObject型
 */
public class QueryBuilder<T extends BaseVo> {
	private T vo;
	private Connection con;
	private String tableName;
	private HashMap<String, Field> columns;
	private HashMap<String, Field> primaryKies;

	/**
	 * コンストラクタ.
	 * 
	 * @param ValueObject
	 * @param DBコネクション
	 * @throws Exception
	 */
	public QueryBuilder(T vo, Connection con) throws Exception {
		this.vo = vo;
		this.con = con;
		getTableName();
		getColumnInfos();
	}

	/**
	 * Insert用PreparedStatement取得.
	 * 
	 * @return PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getInsertPstmt() throws Exception {
		String sql = getInputSql();
		PreparedStatement pstmt = con.prepareStatement(sql);
		setValues(pstmt);
		return pstmt;
	}

	/**
	 * Update用PreparedStatement取得.
	 * 
	 * @return PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getUpdatePstmt() throws Exception {
		String sql = getUpdateSql();
		PreparedStatement pstmt = con.prepareStatement(sql);
		setValues(pstmt);
		setPrimaryKies(pstmt);
		return pstmt;
	}

	/**
	 * Input文取得
	 * 
	 * @return SQL文
	 */
	public String getInputSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(tableName);
		sb.append(" (");
		sb.append(insertColumnNames());
		sb.append(") VALUES(");
		sb.append(insertQuestionMarks());
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Update文取得.
	 * 
	 * @return SQL文
	 */
	public String getUpdateSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(tableName);
		sb.append(" SET ");
		sb.append(insertSetArgPairs());
		sb.append(" WHERE ");
		sb.append(insertPrimaryKey());
		return sb.toString();
	}

	private String insertColumnNames() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (String key : columns.keySet()) {
			sb.append(tableName);
			sb.append(".");
			sb.append(key);
			if (++i < columns.size()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	private String insertQuestionMarks() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < columns.size(); i++) {
			sb.append("?");
			if (i < columns.size() - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	private String insertSetArgPairs() {
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for (String key : columns.keySet()) {
			sb.append(key);
			sb.append(" = ?");
			if (i++ < columns.size()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	private String insertPrimaryKey() {
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for (String key : primaryKies.keySet()) {
			try {
				sb.append(key);
				sb.append(" = ?");
				if (i < primaryKies.size()) {
					sb.append(" AND ");
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private void setValues(PreparedStatement pstmt) {
		int i = 1;
		for (String key : columns.keySet()) {
			Field f = columns.get(key);
			try {
				pstmt.setObject(i++, f.get(vo));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void setPrimaryKies(PreparedStatement pstmt) {
		int i = columns.size();
		for (String key : primaryKies.keySet()) {
			Field f = columns.get(key);
			try {
				pstmt.setObject(++i, f.get(vo));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void getTableName() throws Exception {
		Table t = vo.getClass().getAnnotation(Table.class);
		if (t == null) {
			throw new Exception("");
		}
		this.tableName = t.value();
	}

	private void getColumnInfos() {
		HashMap<String, Field> columns = new HashMap<String, Field>();
		HashMap<String, Field> primaryKies = new HashMap<String, Field>();
		Field[] declaredFields = vo.getClass().getDeclaredFields();
		for (Field f : declaredFields) {
			Column c = f.getAnnotation(Column.class);
			try {
				f.setAccessible(true);
				if (c != null && f.get(vo) != null) {
					columns.put(c.physic(), f);
					if (c.isPrimaryKey()) {
						primaryKies.put(c.physic(), f);
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		this.columns = columns;
		this.primaryKies = primaryKies;
	}
}
