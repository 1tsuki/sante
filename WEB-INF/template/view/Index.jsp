<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>

<section class="caption">
    <h1>santeへようこそ</h1>
    <p>まずは一週間！santeを使って無理なく、健康的な自炊生活を続けてみませんか？</p>
</section>

<section class="login">
    <div class="login-wrapper"></div>
    <div class="login-input">
        <form action="<% v.getPath("/auth/Login"); %>" method="post">
            <div class="column12"><input type="email" name="email" placeholder="登録メールアドレス" required></div>
            <div class="column8"><input type="password" name="password" placeholder="パスワード" required></div>
            <div class="column4"><input type="submit" value="ログイン" class="btn btn-primary"></div>
        </form>
        <div class="login-reissue"><span><a href="<% v.getPath("/auth/Reissue"); %>">パスワードを忘れた方はこちら</a></span></div>
    </div>
    <div class="login-register">
        <span><a href="<% v.getPath("/register/Input"); %>" class="btn btn-info">新規登録する</a></span>
    </div>
</section>

<section class="about">
    <h2>santeとは？</h2>
    <p>「自炊は続けたいけれど、レシピ選びや食材管理が面倒でついつい外食に…」そんな経験ありませんか？santeはそんな貴方に、毎日その日のおすすめレシピをメールで貴方にお届けするサービスです。</p>
</section>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>