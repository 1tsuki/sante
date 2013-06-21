<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="java.util.*"%>
<%@ page import="com.astrider.sfc.app.lib.util.StringUtils"%>
<%@ page import="com.astrider.sfc.src.model.vo.db.*"%>
<%@ page import="com.astrider.sfc.src.model.vo.form.*"%>
<%@ page import="com.astrider.sfc.src.helper.ViewHelper"%>
<%@ page import="static com.astrider.sfc.ApplicationContext.*"%>
<%
  request.setCharacterEncoding("UTF-8");
  ViewHelper v = new ViewHelper(pageContext, session, request);
  pageContext.setAttribute("isLoggedIn", v.isLoggedIn());
%>