package com.astrider.sfc.app.lib.helper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import com.astrider.sfc.app.lib.helper.annotation.Column;
import com.astrider.sfc.app.lib.helper.annotation.Table;
import com.astrider.sfc.app.lib.model.vo.BaseVo;

/**
 * 概要<br>
 *  Insert, Updateのみに対応したQueryBuilder<br>
 *  Voのアノテーション情報を活用したSQL文の発行、PreparedStatementの作成、値の挿入まで<br>
 *<br>
 * @author ItsukiSakitsu
 *
 * @param <T>
 */
public class QueryBuilder<T extends BaseVo> {
    private T vo;
    private Connection con;
    private String tableName;
    private HashMap<String, Field> columns;
    private HashMap<String, Field> primaryKies;

    public QueryBuilder(T vo, Connection con) throws Exception {
        this.vo = vo;
        this.con = con;
        getTableName();
        getColumnInfos();
    }

    public PreparedStatement getInsertPstmt() throws Exception {
        String sql = getInputSql();
        PreparedStatement pstmt = con.prepareStatement(sql);
        setValues(pstmt);
        return pstmt;
    }

    public PreparedStatement getUpdatePstmt() throws Exception {
        String sql = getUpdateSql();
        PreparedStatement pstmt = con.prepareStatement(sql);
        setValues(pstmt);
        setPrimaryKies(pstmt);
        return pstmt;
    }

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

