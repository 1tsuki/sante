<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>
      <div id="content">
        <section class="notification">
          <h1>メールアドレスの確認に成功しました。</h1>
          <p>
            お客様のメールアドレスの確認に成功しました。<br>
            <a href="<% v.getPath("/user/Index"); %>">トップページ</a>に移動してサービスをご利用ください！
          </p>
        </section>
      </div>

<%@ include file="/WEB-INF/template/include/Sidebar.jsp" %>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>