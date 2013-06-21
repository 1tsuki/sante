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
            <h2>ææä¸è¦§</h2>
                <c:forEach var="material" items="${ materialQuantities }" varStatus="status">
                <div class="material">
                    <span class="material-name"><c:out value="${ material.materialName }" /></span>
                    <span class="material-quantity">
                        <c:out value="${ material.prefix }" />
                        <fmt:formatNumber value="${ material.quantity }" pattern="###.#" />
                        <c:out value="${ material.postfix }" />
                    </span>
                </div>
                </c:forEach>
            </section>

        <section class="procedures">
            <h2>ã¤ãããã</h2>
            <c:forEach var="step" items="${ steps }" varStatus = "status">
            <div class="procedure"><p><b><c:out value="${step.step}" />.</b><br> <c:out value="${ step.description }" /></p></div>
            </c:forEach>
            <div class="procedure complete"><a href="<% v.getPath("/user/log/CookComplete?recipe_id=" + recipe.getRecipeId()); %>">å®æãããã¯ãªãã¯ï¼</a></div>
        </section>
    </section>

    <section class="recommend">
        <h2>ãã®ä»ã®ããããã¬ã·ã</h2>
        <c:forEach var="recommendedRecipe" items="${recommendedRecipes}" varStatus = "status">
            <% RecipeVo recommendedRecipe = (RecipeVo) pageContext.getAttribute("recommendedRecipe"); %>
            <div class="recipe">
            <a href="<% v.getPath("/user/recipe/Detail?recipe_id=" + recommendedRecipe.getRecipeId()); %>">
                <img alt="${recommendedRecipe.recipeName}" src="<% v.getPath(recommendedRecipe.getImageUrl()); %>">
                <h3><c:out value="${ recommendedRecipe.recipeName }" /></h3>
            </a>
            </div>
        </c:forEach>
        <a href="<% v.getPath("/user/recipe/Search"); %>" class="btn btn-primary">ãã£ã¨è¦ã</a>
    </section>
</div>

<%@ include file="/WEB-INF/template/include/Sidebar.jsp" %>
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>