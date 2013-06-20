<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>

<section class="status">
    <nav class="status-nav">
        <ul>
            <li><a href="#" onClick="santeUserNutrients.interface.update(0)">今週</a></li>
            <li><a href="#" onClick="santeUserNutrients.interface.update(1)">1週前</a></li>
            <li><a href="#" onClick="santeUserNutrients.interface.update(2)">2週前</a></li>
            <li><a href="#" onClick="santeUserNutrients.interface.update(3)">3週前</a></li>
        </ul>
    </nav>
    <div class="status-contents">
        <div class="calendar">
            <span class="today">日付</span>
        </div>
        <div class="status-counter">
            <dl><dt>自炊回数</dt><dd id="total-cooked">--回</dd></dl>
            <dl><dt>連続自炊日数</dt><dd id="consecutively-cooked">--日</dd></dl>
            <dl><dt>栄養バランス</dt><dd id="nutrients-balance">--%</dd></dl>
        </div>
        <canvas class="pie-graph"></canvas>
        <div class="nutrient-modifier">
            <div class="title">
                <span>調整</span>
            </div>
            <div class="nutrient">
                <span>乳</span>
                <button class="plus" onClick="santeUserNutrients.interface.addNutrient(1, 50);">+</button>
                <button class="minus" onClick="santeUserNutrients.interface.addNutrient(1, -50);">-</button>
            </div>
            <div class="nutrient">
                <span>卵</span>
                <button class="plus" onClick="santeUserNutrients.interface.addNutrient(2, 50);">+</button>
                <button class="minus" onClick="santeUserNutrients.interface.addNutrient(2, -50);">-</button>
            </div>
            <div class="nutrient">
                <span>肉</span>
                <button class="plus" onClick="santeUserNutrients.interface.addNutrient(3, 50);">+</button>
                <button class="minus" onClick="santeUserNutrients.interface.addNutrient(3, -50);">-</button>
            </div>
            <div class="nutrient">
                <span>豆</span>
                <button class="plus" onClick="santeUserNutrients.interface.addNutrient(4, 50);">+</button>
                <button class="minus" onClick="santeUserNutrients.interface.addNutrient(4, -50);">-</button>
            </div>
            <div class="nutrient">
                <span>菜</span>
                <button class="plus" onClick="santeUserNutrients.interface.addNutrient(5, 50);">+</button>
                <button class="minus" onClick="santeUserNutrients.interface.addNutrient(5, -50);">-</button>
            </div>
            <div class="nutrient">
                <span>果</span>
                <button class="plus" onClick="santeUserNutrients.interface.addNutrient(6, 50);">+</button>
                <button class="minus" onClick="santeUserNutrients.interface.addNutrient(6, -50);">-</button>
            </div>
            <div class="nutrient">
                <span>藻</span>
                <button class="plus" onClick="santeUserNutrients.interface.addNutrient(7, 50);">+</button>
                <button class="minus" onClick="santeUserNutrients.interface.addNutrient(7, -50);">-</button>
            </div>
            <div class="nutrient">
                <span>米</span>
                <button class="plus" onClick="santeUserNutrients.interface.addNutrient(8, 50);">+</button>
                <button class="minus" onClick="santeUserNutrients.interface.addNutrient(8, -50);">-</button>
            </div>
            <div class="nutrient">
                <span>芋</span>
                <button class="plus" onClick="santeUserNutrients.interface.addNutrient(9, 50);">+</button>
                <button class="minus" onClick="santeUserNutrients.interface.addNutrient(9, -50);">-</button>
            </div>
            <div class="nutrient">
                <span>油</span>
                <button class="plus" onClick="santeUserNutrients.interface.addNutrient(10, 10);">+</button>
                <button class="minus" onClick="santeUserNutrients.interface.addNutrient(10, -10);">-</button>
            </div>
            <div class="nutrient">
                <span>糖</span>
                <button class="plus" onClick="santeUserNutrients.interface.addNutrient(11, 5);">+</button>
                <button class="minus" onClick="santeUserNutrients.interface.addNutrient(11, -5);">-</button>
            </div>
        </div>
    </div>
</section>

<section class="tips">
    <h2>今日の栄養状態</h2>
    <p>　あなたの食事履歴から算出された不足食材は以下の2種類です。<br>
        <ul>
            <li><c:out value="${ nutrients.primaryNutrientName }" /></li>
            <li><c:out value="${ nutrients.secondaryNutrientName }" /></li>
        </ul>
    </p>
</section>

<section class="recommends-wrapper">
    <h2>あなたに合わせたおすすめレシピ</h2>
    <div class="recommends">
        <c:forEach var="recipe" items="${ recommendedRecipes }" varStatus="status">
            <% RecipeVo recipe = (RecipeVo) pageContext.getAttribute("recipe"); %>
            <c:if test="${0 == status.index}">
                <a class="recipe recipe-main" href="<% v.getPath("/user/recipe/Detail?recipe_id=" + recipe.getRecipeId()); %>">
                    <img alt="料理名" src="<% v.getPath(((RecipeVo)pageContext.getAttribute("recipe")).getImageUrl()); %>">
                    <h3><c:out value="${ recipe.recipeName }" /></h3>
                </a>
            </c:if>
            <c:if test="${0 < status.index}">
                <a class="recipe" href="<% v.getPath("/user/recipe/Detail?recipe_id=" + recipe.getRecipeId()); %>">
                    <img alt="料理名" src="<% v.getPath(((RecipeVo)pageContext.getAttribute("recipe")).getImageUrl()); %>">
                    <h3><c:out value="${ recipe.recipeName }" /></h3>
                </a>
            </c:if>
        </c:forEach>
    </div>
    <p>あなたの過去の調理履歴を参考に、体に不足しているであろう食材を中心としたレシピをピックアップしています。</p>
    <a href="<% v.getPath("/user/recipe/Search"); %>" class="btn btn-primary">もっと見る</a>
</section>

<section class="materials-wrapper">
    <h2>体に不足している食材から選ぶ</h2>
    <h3>食材名から検索</h3>
    <div class="search">
        <form method="POST" action="<% v.getPath("/user/recipe/Search"); %>">
            <input class="input-middle" type="search" name="material_name" placeholder="使いたい食材名を入力" required>
            <input class="btn btn-primary btn-small" type="submit" value="検索">
        </form>
    </div>

    <h3>食材分類から選ぶ</h3>
    <div class="materials">
        <div id="milk"    class="material material-red-light"><a href="<% v.getPath("/user/recipe/Search?nutrient_id=1"); %>">乳製品</a></div>
        <div id="egg"     class="material material-red-light"><a href="<% v.getPath("/user/recipe/Search?nutrient_id=2"); %>">卵</a></div>
        <div id="meat"    class="material material-red-light"><a href="<% v.getPath("/user/recipe/Search?nutrient_id=3"); %>">魚・肉類</a></div>
        <div id="bean"    class="material material-red-light"><a href="<% v.getPath("/user/recipe/Search?nutrient_id=4"); %>">豆類</a></div>
        <div id="veg"     class="material material-green-light"><a href="<% v.getPath("/user/recipe/Search?nutrient_id=5"); %>">野菜</a></div>
        <div id="fruit"   class="material material-green-light"><a href="<% v.getPath("/user/recipe/Search?nutrient_id=6"); %>">果物</a></div>
        <div id="mineral" class="material material-green-light"><a href="<% v.getPath("/user/recipe/Search?nutrient_id=7"); %>">海藻・茸</a></div>
        <div id="crop"    class="material material-yellow-light"><a href="<% v.getPath("/user/recipe/Search?nutrient_id=8"); %>">穀物</a></div>
        <div id="potato"  class="material material-yellow-light"><a href="<% v.getPath("/user/recipe/Search?nutrient_id=9"); %>">イモ類</a></div>
        <div id="fat"     class="material material-yellow-light"><a href="<% v.getPath("/user/recipe/Search?nutrient_id=10"); %>">油分</a></div>
        <div id="sugar"   class="material material-yellow-light"><a href="<% v.getPath("/user/recipe/Search?nutrient_id=11"); %>">糖分</a></div>
    </div>
    <p>最近不足している食材が強調表示されています。</p>
</section>

<%@ include file="/WEB-INF/template/include/Footer.jsp" %>