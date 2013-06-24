<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>
<section class="caption">
    <h1>santéへようこそ</h1>
    <p>
        調理履歴を管理することで、あなたの体調に合わせたレシピを選出するサービスです。
    </p>
</section>

<section class="login">
    <div class="login-wrapper"></div>
    <div class="login-input">
        <form action="<% v.getPath("/auth/Login"); %>" method="post">
            <div class="login-email"><input type="email" name="email" placeholder="メールアドレス" required></div>
            <div class="login-password"><input type="password" name="password" placeholder="パスワード" required></div>
            <div class="login-submit"><input type="submit" value="ログイン" class="btn btn-primary"></div>
        </form>
        <div class="login-reissue"><span><a href="<% v.getPath("/auth/Reissue"); %>">パスワードを忘れた方はこちら</a></span></div>
    </div>
    <div class="login-register">
        <span><a href="<% v.getPath("/register/Input"); %>" class="btn btn-info">新規登録する</a></span>
    </div>
</section>

<section class="about">
    <aside class="howto">
        <h2>栄養状態をグラフで可視化</h2>
        <img alt="栄養状態をグラフで可視化" src="<% v.getPath("/asset/img/chart.jpg"); %>">
        <p>貴方の選んだレシピを記録し、食事を通じて摂取した栄養素を記録します。</p>
    </aside>
    <aside class="howto">
        <h2>レシピ推薦システム</h2>
        <img alt="レシピ推薦システム" src="<% v.getPath("/asset/img/recipe.jpg"); %>">
        <p>記録した調理履歴を参考に、あなたの体に合わせたバランスの良いレシピを推薦します。</p>
    </aside>
    <aside class="howto">
        <h2>おすすめレシピをメール通知</h2>
        <img alt="おすすめレシピをメール通知" src="<% v.getPath("/asset/img/send.jpg"); %>">
        <p>毎日午後5時におすすめレシピをメールで通知。帰りに必要な食材を買って帰ることができます。</p>
    </aside>
</section>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>