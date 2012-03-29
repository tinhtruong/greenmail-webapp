package me.tinhtruong.greenmailwebapp.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import me.tinhtruong.greenmailwebapp.utils.Constants;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;

public class BootstrapServlet extends HttpServlet {
	
	private static final long serialVersionUID = 2683788480108268975L;

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
	}
	
	@Override
	public void destroy() {
		GreenMail greenMail = (GreenMail) getServletContext().getAttribute(Constants.GREENMAIL_INSTANCE_ATTRIBUTE_NAME);
		greenMail.stop();
		super.destroy();
	}
}
