<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>
      <div id="content">
        <section class="register-form">
          <h1>新規登録</h1>
          <form action="<% v.getPath("/register/SendMail"); %>" method="post">
            <fieldset>
              <legend>ユーザー情報</legend>
              <div class="control-group">
                <label class="control-label" for="username">ユーザー名</label>
                <div class="controls">
                    <input type="text" class="input-middle" id="username" name="username" placeholder="username" pattern="[a-zA-Z0-9]+" required>
                    <p class="help-block">半角英数字のみ</p>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="email">メールアドレス</label>
                <div class="controls">
                    <input type="email" class="input-middle" id="email" name="email" placeholder="sample@email.com" required>
                    <p class="help-block"></p>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="password">パスワード</label>
                <div class="controls">
                    <input type="password" class="input-middle" id="password" name="password" placeholder="password" required>
                    <p class="help-block"></p>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="password-confirm">パスワード(確認用)</label>
                <div class="controls">
                    <input type="password" class="input-middle" id="password-confirm" name="password-confirm" placeholder="password" required>
                    <p class="help-block">半角英数記号のみ8文字以上、全てを1回以上利用すること</p>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="male">性別</label>
                <div class="controls">
                    <input type="radio" id="male" name="male" value="true" checked> 男性
                    <input type="radio" id="male" name="male" value="false"> 女性
                    <p class="help-block"></p>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="height">身長(任意)</label>
                <div class="controls">
                    <input type="number" class="input-small" id="height" min=0 max=999 name="height"><span>cm</span>
                    <p class="help-block"></p>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="weight">体重(任意)</label>
                <div class="controls">
                    <input type="number" class="input-small" id="weight" min=0 max=999 name="weight"><span>kg</span>
                    <p class="help-block"></p>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="age">年代(任意)</label>
                <div class="controls">
                    <select class="input-small" id="age" name="age">
                      <option value="" selected>選択する</option>
                      <option value="10">10代</option>
                      <option value="20">20代</option>
                      <option value="30">30代</option>
                      <option value="40">40代</option>
                      <option value="50">50代</option>
                      <option value="60">60代</option>
                      <option value="70">それ以上</option>
                    </select>
                    <p class="help-block"></p>
                </div>
              </div>

              <div class="form-actions">
                <button type="reset" class="btn">キャンセル</button>
                <button type="submit" class="btn btn-primary">仮登録する</button>
              </div>

            </fieldset>
          </form>
        </section>
      </div>

<%@ include file="/WEB-INF/template/include/Sidebar.jsp" %>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>