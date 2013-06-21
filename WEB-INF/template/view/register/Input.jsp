<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>
<% pageContext.setAttribute("SESSION_REGISTER_FORM", SESSION_REGISTER_FORM); %>
      <div id="content">
        <section class="register-form">
          <h1>新規登録</h1>
          <form action="<% v.getPath("/register/SendMail"); %>" method="post">
            <fieldset>
              <legend>ユーザー情報</legend>
              <div class="control-group">
                <label class="control-label" for="username">ユーザー名</label>
                <div class="controls">
                  <c:choose>
                    <c:when test="${empty sessionScope[SESSION_REGISTER_FORM].userName}">
                      <c:set var="username" value="" />
                    </c:when>
                    <c:otherwise>
                      <c:set var="username" value="${sessionScope[SESSION_REGISTER_FORM].userName}" />
                    </c:otherwise>
                  </c:choose>
                  <input type="text" class="input-middle" id="username" name="username" placeholder="username" pattern="[a-zA-Z0-9]+" value="<c:out value="${username}" />" required>
                  <p class="help-block">半角英数字のみ</p>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="email">メールアドレス</label>
                <div class="controls">
                  <c:set var="email" value="${sessionScope[SESSION_REGISTER_FORM].email}" />
                  <input type="email" class="input-middle" id="email" name="email" placeholder="sample@email.com" value="<c:out value="${email}" />" required>
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
                  <c:choose>
                    <c:when test="${empty sessionScope[SESSION_REGISTER_FORM].male}">
                      <c:set var="male" value="${true}" />
                    </c:when>
                    <c:otherwise>
                      <c:set var="male" value="${sessionScope[SESSION_REGISTER_FORM].male}" />
                    </c:otherwise>
                  </c:choose>
                  <input type="radio" id="male" name="male" value="true" <c:if test="${male}">checked</c:if>> 男性
                  <input type="radio" id="male" name="male" value="false" <c:if test="${!male}">checked</c:if>> 女性
                  <p class="help-block"></p>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="height">身長(任意)</label>
                <div class="controls">
                  <c:set var="height" value="${sessionScope[SESSION_REGISTER_FORM].height}" />
                  <input type="number" class="input-small" id="height" min=0 max=999 name="height" value="<c:if test="${height != 0}"><c:out value="${height}" /></c:if>" ><span>cm</span>
                  <p class="help-block"></p>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="weight">体重(任意)</label>
                <div class="controls">
                  <c:set var="weight" value="${sessionScope[SESSION_REGISTER_FORM].weight}" />
                  <input type="number" class="input-small" id="weight" min=0 max=999 name="weight" value="<c:if test="${weight != 0}"><c:out value="${weight}" /></c:if>"  ><span>kg</span>
                  <p class="help-block"></p>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="age">年代(任意)</label>
                <div class="controls">
                  <c:set var="age" value="${sessionScope[SESSION_REGISTER_FORM].age}" />
                  <select class="input-small" id="age" name="age">
                    <option value="" <c:if test="${age == 0}">selected</c:if>>選択する</option>
                    <option value="10" <c:if test="${age == 10}">selected</c:if>>10代</option>
                    <option value="20" <c:if test="${age == 20}">selected</c:if>>20代</option>
                    <option value="30" <c:if test="${age == 30}">selected</c:if>>30代</option>
                    <option value="40" <c:if test="${age == 40}">selected</c:if>>40代</option>
                    <option value="50" <c:if test="${age == 50}">selected</c:if>>50代</option>
                    <option value="60" <c:if test="${age == 60}">selected</c:if>>60代</option>
                    <option value="70" <c:if test="${age == 70}">selected</c:if>>それ以上</option>
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