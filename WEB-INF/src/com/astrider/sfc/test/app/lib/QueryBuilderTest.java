package com.astrider.sfc.test.app.lib;

import static org.mockito.Mockito.*;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.astrider.sfc.app.lib.QueryBuilder;
import com.astrider.sfc.src.model.vo.db.UserVo;

public class QueryBuilderTest {
    private UserVo vo = new UserVo();

    public QueryBuilderTest() {
        vo.setUserId(5);
        vo.setWeight(60);
        vo.setConfirmed(true);
        vo.setMale(true);
        vo.setDeletedAt(new Date(System.currentTimeMillis()));
        vo.setAuthToken("authToken");
        vo.setEmailToken("emailToken");
        vo.setDeleted(true);
        vo.setHeight(170);
        vo.setUserName("astrider");
        vo.setEmail("hogemoge.com");
        vo.setAge(22);
    }

    @Test
    public void 正常なSQL文を出力() {
        Connection con = mock(Connection.class);
        try {
            QueryBuilder<UserVo> qb = new QueryBuilder<UserVo>(vo, con);
            System.out.println(qb.getInputSql());
            System.out.println(qb.getUpdateSql());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insert正常系テスト() {
        Connection con = mock(Connection.class);
        PreparedStatement pstmt = mock(PreparedStatement.class);
        try {
            when(con.prepareStatement(anyString())).thenReturn(pstmt);
            ArgumentCaptor<Object> objCaptor = ArgumentCaptor.forClass(Object.class);
            doNothing().when(pstmt).setObject(anyInt(), objCaptor.capture());
            QueryBuilder<UserVo> qb = new QueryBuilder<UserVo>(vo, con);
            qb.getInsertPstmt();
            System.out.println(objCaptor.getAllValues());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void update正常系テスト() {
        Connection con = mock(Connection.class);
        PreparedStatement pstmt = mock(PreparedStatement.class);
        try {
            when(con.prepareStatement(anyString())).thenReturn(pstmt);
            ArgumentCaptor<Object> objCaptor = ArgumentCaptor.forClass(Object.class);
            doNothing().when(pstmt).setObject(anyInt(), objCaptor.capture());
            QueryBuilder<UserVo> qb = new QueryBuilder<UserVo>(vo, con);
            qb.getUpdatePstmt();
            System.out.println(objCaptor.getAllValues());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
