<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.astrider.sfc.app.helper.ViewUtils"%>
<section id="sidebar">
    <c:if test="${ isLoggedIn }">
    <aside>
        <h2>栄養バランス表</h2>
        <canvas class="sidebar-graph"></canvas>
        <a href="<% v.getPath("/knowledge/Index"); %>" class="btn btn-info">栄養状態グラフについて</a>
    </aside>
    </c:if>
    <aside>
        <h2>広告</h2>
        <img alt="広告" src="<% v.getPath("/asset/img/ad.jpg"); %>">
    </aside>
</section>