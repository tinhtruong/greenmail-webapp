<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="me.tinhtruong.greenmailwebapp.utils.Constants"%>

<div>
	<form class="form-horizontal"
		action="${pageContext.request.contextPath }/restart" method="POST">
		<fieldset>
			<legend>GreenMail protocols</legend>
			<c:set var="errorMessagesAttr" value="<%= Constants.ERROR_MESSAGES%>"/>
			<c:set var="messageAttr" value="<%= Constants.MESSAGE%>"/>
			<c:choose>
                <c:when test="${not empty(sessionScope[messageAttr])}">
                    <div class="alert alert-success">
                        ${sessionScope[messageAttr]}
                        <%
                            session.removeAttribute(Constants.MESSAGE);
                        %>
                    </div>        
                </c:when>
                <c:when test="${not empty sessionScope[errorMessagesAttr]}">
                    <div class="alert alert-error">
                    <c:forEach items="${sessionScope[errorMessagesAttr]}" var="error">
                        <p>${error}</p>
                    </c:forEach>
                    <%
                        session.removeAttribute(Constants.ERROR_MESSAGES);
                    %>
                    </div>
                </c:when>
                <c:otherwise>
                </c:otherwise>
			</c:choose>
			<table class="table table-condensed">
            <thead>
                <tr>
                    <th><input type="checkbox" name="all"></th>
                    <th>Protocol</th>
                    <th>Port</th>
                </tr>
            </thead>
            <tbody>
            <c:set var="serverSetupAttributeName"
                value="<%= Constants.ALL_SERVER_SETUP_ATTRIBUTE_NAME %>" />
            <c:set var="currentServerSetupAttributeName"
                value="<%= Constants.CURRENT_SERVER_SETUP_ATTRIBUTE_NAME %>" />
            <c:forEach items="${applicationScope[serverSetupAttributeName] }" var="serverSetup">
                <tr>
                    <td>
                    <c:choose>
                    <c:when test="${applicationScope[currentServerSetupAttributeName].get(serverSetup.protocol) != null}">
                    <input type="checkbox" name="protocols" value="${serverSetup.protocol }" checked="checked">
                    </c:when>
                    <c:otherwise>
                    <input type="checkbox" name="protocols" value="${serverSetup.protocol }">
                    </c:otherwise>
                    </c:choose>
                    </td>
                    <td><c:out value="${fn:toUpperCase(serverSetup.protocol) }" /></td>
                    <td><input type="text" name="${serverSetup.protocol }Port" value="${serverSetup.port }" class="span1"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
		</fieldset>
		<button type="submit" class="btn btn-primary">Restart GreenMail</button>
	</form>
</div>