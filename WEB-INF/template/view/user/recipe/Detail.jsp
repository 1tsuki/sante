<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>
<% RecipeVo recipe = (RecipeVo) request.getAttribute("recipe"); %>
<div id="content">
    <section class="recipe-detail">
        <aside class="title">
            <h1 class="recipe-title"><c:out value="${ recipe.recipeName }" /></h1>
            <!-- <div class="control">
                <a class="btn btn-primary">Share</a>
                <a class="btn btn-primary">Like</a>
                <a class="btn btn-primary">Cooked</a>
            </div> -->
        </aside>

        <section class="image">
            <img alt="<c:out value="${ recipe.recipeName }" />" src="<% v.getPath(recipe.getImageUrl()); %>">
        </section>

        <section class="materials">
            <h2>材料一覧</h2>
                <c:forEach var="material" items="${ materialQuantities }" varStatus="status">
                <div class="material">
                    <span class="material-name"><c:out value="${ material.materialName }" /></span>
                    <span class="material-quantity">
                        <c:out value="${ material.prefix }" />
                        <c:if test="${material.quantity != 0}" >
                            <fmt:formatNumber value="${ material.quantity }" pattern="###.#" />
                        </c:if>
                        <c:out value="${ material.postfix }" />
                    </span>
                </div>
                </c:forEach>
            </section>

        <section class="procedures">
            <h2>作り方</h2>
            <c:forEach var="step" items="${ steps }" varStatus = "status">
            <div class="procedure"><p><b><c:out value="${step.step}" />.</b><br> <c:out value="${ step.description }" /></p></div>
            </c:forEach>
            <div class="procedure complete"><a href="<% v.getPath("/user/log/CookComplete?recipe_id=" + recipe.getRecipeId()); %>">完成したらここをクリック</a></div>
        </section>
    </section>

    <section class="recommend">
        <h2>その他のおすすめレシピ</h2>
        <c:forEach var="recommendedRecipe" items="${recommendedRecipes}" varStatus = "status">
            <% RecipeVo recommendedRecipe = (RecipeVo) pageContext.getAttribute("recommendedRecipe"); %>
            <div class="recipe">
            <a href="<% v.getPath("/user/recipe/Detail?recipe_id=" + recommendedRecipe.getRecipeId()); %>">
                <img alt="${recommendedRecipe.recipeName}" src="<% v.getPath(recommendedRecipe.getImageUrl()); %>">
                <h3><c:out value="${ recommendedRecipe.recipeName }" /></h3>
            </a>
            </div>
        </c:forEach>
        <a href="<% v.getPath("/user/recipe/Search"); %>" class="btn btn-primary">もっと見る</a>
    </section>
</div>

<%@ include file="/WEB-INF/template/include/Sidebar.jsp" %>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>