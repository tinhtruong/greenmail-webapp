package me.tinhtruong.greenmailwebapp.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import me.tinhtruong.greenmailwebapp.utils.Constants;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

public class BootstrapServlet extends HttpServlet {
	
	private static final long serialVersionUID = 2683788480108268975L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		GreenMail greenMail = new GreenMail(ServerSetupTest.ALL);
		getServletContext().setAttribute(Constants.GREENMAIL_INSTANCE_ATTRIBUTE_NAME, greenMail);
		getServletContext().setAttribute(Constants.SERVER_SETUP_ATTRIBUTE_NAME, ServerSetupTest.ALL);
		greenMail.start();
	}
	
	@Override
	public void destroy() {
		GreenMail greenMail = (GreenMail) getServletContext().getAttribute(Constants.GREENMAIL_INSTANCE_ATTRIBUTE_NAME);
		greenMail.stop();
		super.destroy();
	}
}
