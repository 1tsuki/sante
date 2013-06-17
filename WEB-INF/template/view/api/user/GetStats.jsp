<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.astrider.sfc.src.model.vo.db.*"%>

{
    "status": {
        "success": "<c:out value="${success}" />",
        "message": "<c:out value="${message}" />"
    },
    "result": {
        "stats": {
            "totalCooked"       : "<c:out value="${userStats.totalCooked}" />",
            "consecutivelyCooked"   : "<c:out value="${userStats.consecutivelyCooked}" />",
            "maxConsecutivelyCooked": "<c:out value="${userStats.maxConsecutivelyCooked}" />",
            "nutrientsBalance"  : "<c:out value="${userStats.nutrientsBalance}" />"
        }
    }
}