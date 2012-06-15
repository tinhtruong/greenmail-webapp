<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="me.tinhtruong.greenmailwebapp.utils.Constants"%>
<%@ page import="me.tinhtruong.greenmailwebapp.utils.Utils"%>
<%@ page import="com.icegreen.greenmail.util.GreenMailUtil"%>
<%@ page import="com.icegreen.greenmail.util.GreenMail"%>
<%@ page import="javax.mail.internet.MimeMessage"%>
<%@ page import="javax.mail.internet.MimeMessage.*"%>


<table class="table table-striped table-condensed">
    <thead>
        <tr>
            <th id="from">From</th>
            <th>Subject</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <%
        GreenMail greenMail = (GreenMail) application.getAttribute(Constants.GREENMAIL_INSTANCE_ATTRIBUTE_NAME);
        if (greenMail.getReceivedMessages().length == 0) {
        %>
        <tr>
            <td colspan="3">There is no message</td>
        </tr>
        <%
        } else {
            int i = 0;
            for (MimeMessage message : greenMail.getReceivedMessages()) {
            i ++;
        %>
        <div class="modal hide" id="dialog-<%=i%>">
            <div class="modal-body">
                <label>Subject: <%=message.getSubject()%></label>
                <label>From: <%=GreenMailUtil.getAddressList(message.getFrom())%></label>
                <label>To: <%=GreenMailUtil.getAddressList(message.getRecipients(RecipientType.TO))%></label>
                <%
                if (message.getRecipients(RecipientType.CC) != null) {
                %> 
                <label>CC: <%=GreenMailUtil.getAddressList(message.getRecipients(RecipientType.CC))%></label>
                <%}%>
                <%
                if (message.getRecipients(RecipientType.BCC) != null) {
                %>
                <label>BCC: <%=GreenMailUtil.getAddressList(message.getRecipients(RecipientType.BCC))%></label>
                <%}%>
                <hr/>
                <div id="mailContent">
                    <%=Utils.getMessageText(message)%>
                </div>
                
                <%
                if (GreenMailUtil.hasNonTextAttachments(message)) {
                %>
                    <hr/>
                    <div class="attachmentGroup">
                    <ul>
                <%
                    for (String attachedFilename : Utils.getAttachmentFileNames(message)) {
                %>
                        <li>
                        <a href="${pageContext.request.contextPath }/download?<%=Constants.MESSAGE_ID_PARAM%>=<%=message.getMessageID()%>&<%=Constants.FILE_NAME_PARAM%>=<%=attachedFilename%>">
                        <img src="${pageContext.request.contextPath }/img/attachment-icon.png" alt="Attachment" width="16px" height="16px"/>
                        <%=attachedFilename%>
                        </a>
                        </li>
                <%
                    }
                %>
                    </ul>
                    </div>
                <%
                }
                %>      
            </div>
            <div class="modal-footer">
                <a href="#dialog-<%=i%>" class="btn" data-dismiss="modal">Close</a>
            </div>
        </div>
        <tr id="<%=message.getMessageID()%>" data-target="#dialog-<%=i%>" data-toggle="modal">
            <td><%= message.getFrom() == null ? "Anonymous" : message.getFrom()[0].toString()%></td>
            <td><%= message.getSubject()%></td>
            <td>
        <%
                if (GreenMailUtil.hasNonTextAttachments(message)) {
        %>
                <img src="${pageContext.request.contextPath }/img/attachment-icon.png" alt="Attachment" width="16px" height="16px"/>
        <%
                }
        %>
            </td>
        </tr>
        <%
            }
        }
        %>
    </tbody>
</table>
