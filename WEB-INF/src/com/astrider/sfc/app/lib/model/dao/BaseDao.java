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

/*
 * 概要
 *  Dao基底クラス。DBコネクション機能をデフォルトで持つ。
 *  利用後は必ずclose()を実行する必要がある
 *
 * 機能
 *  主要機能
 *      ・insert()   extends BaseVoなオブジェクトを一発Insert
 *      ・update()   extends BaseVoなオブジェクトを一発Update
 */
public class BaseDao {
    private final String localName = "java:comp/env/jdbc/sante";
    private Context      context   = null;
    private DataSource   ds        = null;
    protected Connection con       = null;

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

    public <T extends BaseVo> boolean insert(T vo) {
        boolean succeed = false;
        PreparedStatement pstmt = null;

        try {
            QueryBuilder<T> qb = new QueryBuilder<T>(vo, con);
            pstmt = qb.getInsertPstmt();
            int resultCount = pstmt.executeUpdate();
            if (resultCount == 1) {
                con.commit();
                succeed = true;
            } else {
                con.rollback();
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
