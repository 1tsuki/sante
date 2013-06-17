<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>

<div id="content">
    <section class="notification">
        <h1>お探しのページは見つかりませんでした。</h1>
        <p>ご迷惑をお掛けして申し訳ありません。お客様の指定されたページは見つかりませんでした。<br>お手数ですが、<a href="<% v.getPath("/Index"); %>">トップページ</a>にお戻り下さい。</p>
    </section>
</div>

<%@ include file="/WEB-INF/template/include/Sidebar.jsp" %>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>