<%@ page import="me.tinhtruong.greenmailwebapp.utils.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>GreenMail <%= request.getParameter("page") %></title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
</head>
<body>
    <div class="container-fluid">
        <div class="navbar">
            <div class="navbar-inner">
                <div class="container">
                    <a class="brand" href="${pageContext.request.contextPath}">Greenmail Web Application</a>
                </div>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span2">
                <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp"/>
            </div>
            <div class="span10">
                <% String includePage = "/WEB-INF/jsp/" + request.getParameter(Constants.PAGE_NAME_PARAM) + ".jsp"; %>
                <jsp:include page="<%= includePage %>"/>
            </div>
        </div>
        <div class="row-fluid">
            <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
        </div>
    </div>
</body>
</html>