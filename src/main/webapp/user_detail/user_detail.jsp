<%@ page contentType="text/html; charset=UTF-8" %>

<%
if (request.getAttribute("user") == null) {
    response.sendRedirect(request.getContextPath() + "/user_info_list");
    return;
}
%>

<jsp:include page="/WEB-INF/admin_base.jsp">
    <jsp:param name="pageTitle" value="ユーザー詳細" />
    <jsp:param name="contentPage" value="/user_detail/user_detail_content.jsp" />
</jsp:include>
