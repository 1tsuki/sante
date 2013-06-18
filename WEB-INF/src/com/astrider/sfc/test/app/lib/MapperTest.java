package com.astrider.sfc.test.app.lib;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.astrider.sfc.app.lib.Mapper;
import com.astrider.sfc.test.src.model.vo.TestVo;

public class MapperTest {

    @Test
    public void fromRequestParameter正常リクエスト() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        TestVo expected = new TestVo();
        expected.setA(""); // not null
        expected.setB("abc"); // maxLength=5, minLength=3
        expected.setC(true); // not null
        expected.setD(false); // not null
        expected.setE(0); // max=5, min=-1
        expected.setF(122); // length=3
        expected.setG("asdf"); // notBlank

        when(request.getParameter("a")).thenReturn(expected.getA());
        when(request.getParameter("B")).thenReturn(expected.getB());
        when(request.getParameter("c_boolean")).thenReturn(String.valueOf(expected.isC()));
        when(request.getParameter("d-Boolean")).thenReturn(String.valueOf(expected.getD()));
        when(request.getParameter("e!")).thenReturn(String.valueOf(expected.getE()));
        when(request.getParameter("f123")).thenReturn(String.valueOf(expected.getF()));
        when(request.getParameter("5")).thenReturn(expected.getG());

        Mapper<TestVo> mapper = new Mapper<TestVo>();
        TestVo actual = mapper.fromHttpRequest(request);
        valueMatchSuccess(actual, expected);
    }

    @Test
    public void fromRequestParameter不正リクエスト() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        TestVo expected = new TestVo();
        expected.setA(""); // not null
        expected.setB("abc"); // maxLength=5, minLength=3
        expected.setC(true); // not null
        expected.setD(false); // not null
        expected.setE(0); // max=5, min=-1
        expected.setF(122); // length=3
        expected.setG("asdf"); // notBlank

        when(request.getParameter("a")).thenReturn(expected.getA());
        when(request.getParameter("B")).thenReturn(expected.getB());
        when(request.getParameter("c_boolean")).thenReturn(null);
        when(request.getParameter("d-Boolean")).thenReturn(String.valueOf(expected.getD()));
        when(request.getParameter("e!")).thenReturn(String.valueOf(expected.getE()));
        when(request.getParameter("f123")).thenReturn(String.valueOf(expected.getF()));
        when(request.getParameter("5")).thenReturn(expected.getG());

        Mapper<TestVo> mapper = new Mapper<TestVo>();
        TestVo actual = mapper.fromHttpRequest(request);
        valueMatchFail(actual, expected);
    }

    @Test
    public void fromResultSet正常リクエスト() throws SQLException {
        TestVo expected = new TestVo();
        expected.setA(""); // not null
        expected.setB("abc"); // maxLength=5, minLength=3
        expected.setC(true); // not null
        expected.setD(false); // not null
        expected.setE(0); // max=5, min=-1
        expected.setF(122); // length=3
        expected.setG("asdf"); // notBlank

        ResultSet rs = mock(ResultSet.class);

        when(rs.getString("a")).thenReturn(expected.getA());
        when(rs.getString("B")).thenReturn(expected.getB());
        when(rs.getBoolean("c_boolean")).thenReturn(expected.isC());
        when(rs.getBoolean("d-Boolean")).thenReturn(expected.getD());
        when(rs.getInt("e!")).thenReturn(expected.getE());
        when(rs.getInt("f123")).thenReturn(expected.getF());
        when(rs.getString("5")).thenReturn(expected.getG());

        Mapper<TestVo> mapper = new Mapper<TestVo>();
        TestVo actual = mapper.fromResultSet(rs);
        valueMatchSuccess(actual, expected);
    }

    private void valueMatchSuccess(TestVo actual, TestVo expected) {
        assertTrue(actual.getA().equals(expected.getA()));
        assertTrue(actual.getB().equals(expected.getB()));
        assertTrue(actual.isC() == expected.isC());
        assertTrue(actual.getD().equals(expected.getD()));
        assertTrue(actual.getE() == expected.getE());
        assertTrue(actual.getF().equals(expected.getF()));
        assertTrue(actual.getG().equals(expected.getG()));
    }

    private void valueMatchFail(TestVo actual, TestVo expected) {
        boolean result = true;
        result = actual.getA().equals(expected.getA()) && result;
        result = actual.getB().equals(expected.getB()) && result;
        result = actual.isC() == expected.isC() && result;
        result = actual.getD().equals(expected.getD()) && result;
        result = actual.getE() == expected.getE() && result;
        result = actual.getF().equals(expected.getF()) && result;
        result = actual.getG().equals(expected.getG()) && result;

        assertFalse(result);
    }
}
