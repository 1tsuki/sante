<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>

<div id="content">
	<section class="reissue-form">
		<h1>パスワード再発行</h1>
		<form action="<% v.getPath("/auth/Reissue"); %>" method="post">
			<fieldset>
				<legend>登録情報を入力</legend>
					<div class="control-group">
					   <label class="control-label" for="email">メールアドレス</label>
					<div class="controls">
					   <input type="text" id="email" name="email" class="input-middle" placeholder="メールアドレス" required>
					   <p class="help-block">登録したメールアドレスに仮パスワードが送信されます</p>
					</div>
					</div>
					
					<div class="form-actions">
					   <button type="reset" class="btn" tabindex="1">キャンセル</button>
					   <button type="submit" class="btn btn-primary" tabindex="2">再発行する</button>
					</div>
				</fieldset>
		</form>
	</section>
</div>

<%@ include file="/WEB-INF/template/include/Sidebar.jsp" %>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>