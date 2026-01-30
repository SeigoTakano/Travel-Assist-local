<%@ page contentType="text/html; charset=UTF-8" %>


<%
if (request.getAttribute("userList") == null) {
    response.sendRedirect(request.getContextPath() + "/manager_info_manage");
    return;
}
%>


<jsp:include page="/WEB-INF/admin_base.jsp">
    <jsp:param name="pageTitle" value="管理者情報一覧" />
    <jsp:param name="contentPage" value="/manager_info_manage/manager_info_manage_content.jsp" />
</jsp:include>
