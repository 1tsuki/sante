<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>

<div id="content">
	<section class="recommended-recipes">
	  <h1>おすすめレシピ一覧</h1>
	  <section class="control">
	    <div class="sort-menu">
	      <div class="sort">おすすめ順</div>
	      <div class="sort">調理時間順</div>
	    </div>
	
	    <aside class="search">
	      <input class="input-small" type="search" placeholder="使いたい食材名を入力" required>
	      <input class="btn btn-primary" type="submit" value="検索">
	    </aside>
	
	    <aside class="materials">
	      <span>食材分類から絞込み</span>
	      <div class="material-list">
	        <a class="material" href="./recipe/list.html"><span>乳製品</span></a>
	        <a class="material" href="./recipe/list.html"><span>卵</span></a>
	        <a class="material" href="./recipe/list.html"><span>魚類</span></a>
	        <a class="material" href="./recipe/list.html"><span>肉類</span></a>
	        <a class="material" href="./recipe/list.html"><span>豆類</span></a>
	        <a class="material" href="./recipe/list.html"><span>野菜</span></a>
	        <a class="material" href="./recipe/list.html"><span>イモ類</span></a>
	        <a class="material" href="./recipe/list.html"><span>海藻・きのこ</span></a>
	        <a class="material" href="./recipe/list.html"><span>穀物</span></a>
	        <a class="material" href="./recipe/list.html"><span>甘味</span></a>
	        <a class="material" href="./recipe/list.html"><span>果物</span></a>
	      </div>
	    </aside>
	  </section>
	
	  <section class="recipes">
	    <c:forEach var="recipe" items="${recipes}" varStatus="status">
	       <% RecipeVo recipe = (RecipeVo) pageContext.getAttribute("recipe"); %>
	       <article class="recipe">
		       <a href="<% v.getPath("/user/recipe/Detail?recipe_id=" + recipe.getRecipeId()); %>">
	            <div class="recipe-image-wrapper"><img alt="<c:out value="${recipe.recipeName}"/>" 
	               src="<% v.getPath(recipe.getImageUrl()); %>"></div>
	            <div class="recipe-name-wrapper"><span class="recipe-name"><c:out value="${recipe.recipeName}"/></span></div>
	            <div class="recipe-info-wrapper">
	              <!-- <div class="recipe-materials-wrapper">
	                <span class="recipe-materials-label">材料</span>
	                <span class="recipe-materials"></span>
	              </div> -->
	              <div class="recipe-esttime-wrapper">
	                <span class="recipe-esttime-label">時間</span>
	                <span class="recipe-esttime"><c:out value="${recipe.estimatedTime}" />分</span>
	              </div>
	            </div>
	            <div class="recipe-canvas-wrapper"><canvas id="recipe-5"></canvas></div>
	          </a>
	       </article>
	    </c:forEach>
	  </section>
	</section>
</div>
<%@ include file="/WEB-INF/template/include/Sidebar.jsp" %>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>