<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<%@ page import="com.astrider.sfc.src.model.vo.db.*"%>
<%@ page import="com.astrider.sfc.src.model.vo.form.*"%>
<%@ page import="com.astrider.sfc.src.helper.ViewUtils"%>
<%@ page import="com.astrider.sfc.app.lib.helper.StringUtils"%>
<%	
    request.setCharacterEncoding("UTF-8");
    ViewUtils v = new ViewUtils(pageContext, session, request);
    pageContext.setAttribute("isLoggedIn", v.isLoggedIn());
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title><% v.getTitle(); %></title>
    <link href="<%v.getPath("/asset/css/normalize.css");%>" rel="stylesheet" type="text/css">
    <link href="<%v.getCssPath();%>" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
    <c:if test="${ isLoggedIn }" >
    <script type="text/javascript" src="<% v.getPath("/asset/js/santePieGraph.js"); %>"></script>
    <script type="text/javascript" src="<% v.getPath("/asset/js/santeUserStats.js"); %>"></script>
    <script type="text/javascript" src="<% v.getPath("/asset/js/santeUserNutrients.js"); %>"></script>
    <script type="text/javascript" src="<% v.getPath("/asset/js/santeFillMaterials.js"); %>"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        // prepare for pieGraph
        var params = ["乳", "卵", "肉", "豆", "菜", "果", "藻", "米", "芋", "油", "糖"];
        santePieGraph.interface.init(".pie-graph", params);
        santePieGraph.interface.setSize($('.pie-graph').width(), $('.pie-graph').height());
        santePieGraph.interface.setAuxlineNum(10);
        santePieGraph.interface.draw();

        santeUserNutrients.interface.exec();
      });
    </script>
    </c:if>
  </head>

  <body>
    <% v.showBackgroundImage(); %>
    <header>
      <div id="header">
        <c:if test="${ isLoggedIn }" >
        <section id="header-upper">
          <div class="header-inner">
            <div class ="header-logout">
              <a href="<%v.getPath("/auth/Logout");%>">ログアウト</a>
              <!-- <a href="#">設定</a> -->
            </div>
          </div>
        </section>
        </c:if>
        <section id="header-lower">
          <div class="header-inner">
            <div class="header-logo">
              <a href="<%v.getPath("/Index");%>">sante</a><p>-自炊を楽しむすべての人に、手軽で健康的な料理生活を-</p>
            </div>
            
            <nav class="header-control">
            <c:if test="${ isLoggedIn }" >
              <ul>
                <li><a href="<% v.getPath("/knowledge/Index"); %>"><i></i>栄養知識</a></li>
              </ul>
            </c:if>
            </nav>
          </div>
        </section>
      </div>
      <c:if test="${ isLoggedIn }" >
      <div id="header-upper-dummy"></div>
      </c:if>
      <div id="header-lower-dummy"></div>
    </header>
    
    <div id="wrapper">
      <% v.getFlashMessages(); %>