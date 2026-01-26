<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // セッションからユーザー情報があるか確認
    if (session.getAttribute("username") != null) {
        
        response.sendRedirect(request.getContextPath() + "/menu.jsp");
        
    } else {
        
        // 【未ログイン】ログイン画面へ
        response.sendRedirect(request.getContextPath() + "/login/login.jsp");
        
    }
%>