<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>
      <div id="content">
        <section class="notification">
          <h1>メールアドレスの確認に失敗しました。</h1>
          <p>
            ご迷惑をお掛けして申し訳ありません。お客様のメールアドレスが確認できませんでした。<br>
            仮登録URLの有効期限が過ぎているか、リンクURLが改行されてしまっている可能性があります。<br>
            お手数ですが、<a href="<% v.getPath("/register/Input"); %>">新規登録フォーム</a>より再度登録手続きをお願い致します。
          </p>
        </section>
      </div>

<%@ include file="/WEB-INF/template/include/Sidebar.jsp" %>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>