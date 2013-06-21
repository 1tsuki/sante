<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>

<div id="content">
    <section class="reissue-form">
        <h1>パスワード変更</h1>
        <form action="<% v.getPath("/user/profile/ChangePassword"); %>" method="post">
            <fieldset>
                <legend>変更内容を入力</legend>
                    <div class="control-group">
                        <label class="control-label" for="current">現在のパスワード</label>
	                    <div class="controls">
	                       <input type="password" id="current" name="current" class="input-middle" placeholder="現在のパスワード" required>
	                       <p class="help-block">不正アクセス防止のため、再入力をお願いしています</p>
	                    </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="new">新しいパスワード</label>
                        <div class="controls">
                           <input type="password" id="new" name="new" class="input-middle" placeholder="新しいパスワード" required>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="new_confirm">新しいパスワード(確認用)</label>
                        <div class="controls">
                           <input type="password" id="new_confirm" name="new_confirm" class="input-middle" placeholder="新しいパスワード" required>
                           <p class="help-block">半角英数記号のみ8文字以上、全てを1回以上利用すること</p>
                        </div>
                    </div>

                    <div class="form-actions">
                       <button type="reset" class="btn" tabindex="1">キャンセル</button>
                       <button type="submit" class="btn btn-primary" tabindex="2">変更する</button>
                    </div>
                </fieldset>
        </form>
    </section>
</div>

<%@ include file="/WEB-INF/template/include/Sidebar.jsp" %>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>