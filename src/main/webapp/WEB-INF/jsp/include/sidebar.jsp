<%@ page import="me.tinhtruong.greenmailwebapp.utils.*" %>
<% String pageName = request.getParameter(Constants.PAGE_NAME_PARAM); %>
<ul class="nav nav-list">
	<li class="nav-header">Pages</li>
	<li <%= pageName.equals(Constants.MAIL_PAGE) ? "class='active'" : "" %>><a href="${pageContext.request.contextPath}/?<%=Constants.PAGE_NAME_PARAM %>=<%= Constants.MAIL_PAGE %>">Mails</a></li>
	<li <%= pageName.equals(Constants.SETUP_PAGE) ? "class='active'" : "" %>><a href="${pageContext.request.contextPath}/?<%=Constants.PAGE_NAME_PARAM %>=<%= Constants.SETUP_PAGE %>">Setup</a></li>
	<li <%= pageName.equals(Constants.HELP_PAGE) ? "class='active'" : "" %>><a href="${pageContext.request.contextPath}/?<%=Constants.PAGE_NAME_PARAM %>=<%= Constants.HELP_PAGE %>">Help</a></li>
</ul>