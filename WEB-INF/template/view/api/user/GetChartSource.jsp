<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
{
    "status": {
        "success": "<c:out value="${success}" />",
        "message": "<c:out value="${message}" />"
    },
    "result": {
        "items": [
            <c:out value="${items[0]}" />,
            <c:out value="${items[1]}" />, 
            <c:out value="${items[2]}" />, 
            <c:out value="${items[3]}" />, 
            <c:out value="${items[4]}" />, 
            <c:out value="${items[5]}" />, 
            <c:out value="${items[6]}" />, 
            <c:out value="${items[7]}" />, 
            <c:out value="${items[8]}" />, 
            <c:out value="${items[9]}" />, 
            <c:out value="${items[10]}" /> 
        ]
    }
}