<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>
<div id="content">
  <section class="recommended-recipes">
    <h1><c:out value="${query}" />レシピ一覧</h1>
    <section class="control">
      <aside class="search">
        <form action="<% v.getPath("/user/recipe/Search"); %>" method="post">
          <input class="input-small" type="search" placeholder="使いたい食材名を入力" name="material_name" value="<c:out value="${materialName}" />" required>
          <input class="btn btn-primary" type="submit" value="検索">
        </form>
      </aside>

      <aside class="materials">
        <span>食材分類絞込</span>
        <div class="material-list">
          <a class="material <c:if test="${materialId == 1}">selected</c:if>" href="<% v.getPath("/user/recipe/Search?nutrient_id=1"); %>"><span>乳製品</span></a>
          <a class="material <c:if test="${materialId == 2}">selected</c:if>" href="<% v.getPath("/user/recipe/Search?nutrient_id=2"); %>"><span>卵</span></a>
          <a class="material <c:if test="${materialId == 3}">selected</c:if>" href="<% v.getPath("/user/recipe/Search?nutrient_id=3"); %>"><span>肉・魚類</span></a>
          <a class="material <c:if test="${materialId == 4}">selected</c:if>" href="<% v.getPath("/user/recipe/Search?nutrient_id=4"); %>"><span>豆類</span></a>
          <a class="material <c:if test="${materialId == 5}">selected</c:if>" href="<% v.getPath("/user/recipe/Search?nutrient_id=5"); %>"><span>野菜</span></a>
          <a class="material <c:if test="${materialId == 6}">selected</c:if>" href="<% v.getPath("/user/recipe/Search?nutrient_id=6"); %>"><span>果物</span></a>
          <a class="material <c:if test="${materialId == 7}">selected</c:if>" href="<% v.getPath("/user/recipe/Search?nutrient_id=7"); %>"><span>海藻・茸</span></a>
          <a class="material <c:if test="${materialId == 8}">selected</c:if>" href="<% v.getPath("/user/recipe/Search?nutrient_id=8"); %>"><span>穀物</span></a>
          <a class="material <c:if test="${materialId == 9}">selected</c:if>" href="<% v.getPath("/user/recipe/Search?nutrient_id=9"); %>"><span>イモ類</span></a>
          <a class="material <c:if test="${materialId == 10}">selected</c:if>" href="<% v.getPath("/user/recipe/Search?nutrient_id=10"); %>"><span>油分</span></a>
          <a class="material <c:if test="${materialId == 11}">selected</c:if>" href="<% v.getPath("/user/recipe/Search?nutrient_id=11"); %>"><span>糖分</span></a>
        </div>
      </aside>
    </section>

    <section class="recipes">
      <c:if test="${fn:length(recipes) == 0}">
        <article class="recipe">
          <p>条件に該当するレシピは見つかりませんでした</p>
        </recipe>
      </c:if>
      <c:forEach var="recipe" items="${recipes}" varStatus="status">
        <% RecipeVo recipe = (RecipeVo) pageContext.getAttribute("recipe"); %>
        <article class="recipe">
          <a href="<% v.getPath("/user/recipe/Detail?recipe_id=" + recipe.getRecipeId()); %>">
            <div class="recipe-image-wrapper">
              <img alt="<c:out value="${recipe.recipeName}"/>" src="<% v.getPath(recipe.getImageUrl()); %>">
            </div>
            <div class="recipe-name-wrapper">
              <span class="recipe-name"><c:out value="${recipe.recipeName}"/></span>
            </div>
            <div class="recipe-info-wrapper">
              <div class="recipe-esttime-wrapper">
                <span class="recipe-esttime-label">調理時間</span>
                <span class="recipe-esttime"><c:out value="${recipe.estimatedTime}" />分</span>
              </div>
              <div class="recipe-materials-wrapper">
                <span class="recipe-materials-label">材料一覧</span>
                <span class="recipe-materials">
                  <c:forEach var="material" items="${materials[recipe.recipeId]}" varStatus="status">
                    <c:out value="${material.materialName}" /><c:if test="${not status.last}">,</c:if>
                  </c:forEach>
                </span>
              </div>
            </div>
          </a>
        </article>
      </c:forEach>
    </section>
  </section>
</div>

<%@ include file="/WEB-INF/template/include/Sidebar.jsp" %>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>