package me.tinhtruong.greenmailwebapp.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.tinhtruong.greenmailwebapp.utils.Constants;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;

public class BootstrapServlet extends HttpServlet {
	
	private static final long serialVersionUID = 2683788480108268975L;
	private static final Logger logger = LoggerFactory.getLogger(BootstrapServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		GreenMail greenMail = new GreenMail(ServerSetupTest.ALL);
		getServletContext().setAttribute(Constants.GREENMAIL_INSTANCE_ATTRIBUTE_NAME, greenMail);
		getServletContext().setAttribute(Constants.ALL_SERVER_SETUP_ATTRIBUTE_NAME, ServerSetupTest.ALL);
		Map<String, ServerSetup> currentServerSetup = new HashMap<String, ServerSetup>();
		for (ServerSetup serverSetup : ServerSetupTest.ALL) {
			currentServerSetup.put(serverSetup.getProtocol(), serverSetup);
		}
		getServletContext().setAttribute(Constants.CURRENT_SERVER_SETUP_ATTRIBUTE_NAME, currentServerSetup);
		greenMail.start();
		
		
		if ("true".equalsIgnoreCase(config.getInitParameter("development"))) {
			sendSampleEmails(greenMail);
		}
	}
	
	private void sendSampleEmails(GreenMail greenMail) {
		try {
			for (int i = 0; i < 10; i ++) {
				GreenMailUtil.sendTextEmailTest("test@aaa.com", "test@bbb.com", "Test Email", "<h1>This is the content</h1>");
				GreenMailUtil.sendAttachmentEmail("test@aaa.com", "test@bbb.com", "With Attachment", "This is the attached email", prepareSampleAttachment(), "application/pdf", "TinhTruong_Resume.doc", "Sample description", ServerSetupTest.SMTP);
			}
		} catch (Exception e) {
			logger.error("Failed to send sample emails in development", e);
		}
	}
	
	private byte[] prepareSampleAttachment() throws FileNotFoundException, IOException {
		File sampleAttachment = new File("/home/tinh/TinhTruong_Resume.doc");
		byte[] content = new byte[(int)sampleAttachment.length()];
		FileInputStream stream = new FileInputStream(sampleAttachment);
		stream.read(content);
		stream.close();
		return content;
	}
	
	@Override
	public void destroy() {
		GreenMail greenMail = (GreenMail) getServletContext().getAttribute(Constants.GREENMAIL_INSTANCE_ATTRIBUTE_NAME);
		greenMail.stop();
		super.destroy();
	}
}
