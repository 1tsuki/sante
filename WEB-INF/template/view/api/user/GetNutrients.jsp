<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.astrider.sfc.app.model.vo.db.*"%>

{
    "status": {
        "success": "<c:out value="${success}" />",
        "message": "<c:out value="${message}" />"
    },
    "result": {
        "nutrients": {
            "ingested": {
                "milk"      : <c:out value="${ingested.milk}" />,
                "egg"       : <c:out value="${ingested.egg}" />,
                "meat"      : <c:out value="${ingested.meat}" />,
                "bean"      : <c:out value="${ingested.bean}" />,
                "vegetable" : <c:out value="${ingested.vegetable}" />,
                "potato"    : <c:out value="${ingested.potato}" />,
                "fruit"     : <c:out value="${ingested.fruit}" />,
                "mineral"   : <c:out value="${ingested.mineral}" />,
                "crop"      : <c:out value="${ingested.crop}" />,
                "fat"       : <c:out value="${ingested.fat}" />,
                "suguar"    : <c:out value="${ingested.suguar}" />
            },
            "desired": {
                <c:forEach var="nutrient" items="${desired}" varStatus="status">
                "<c:out value="${nutrient.nutrientName}" />" : <c:out value="${nutrient.dailyRequiredAmount * mealCount}" />
                <c:if test="${!status.last}">,</c:if>
                </c:forEach>
            }
        }
    }
}