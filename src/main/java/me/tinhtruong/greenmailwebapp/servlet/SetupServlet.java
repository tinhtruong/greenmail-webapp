package me.tinhtruong.greenmailwebapp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import me.tinhtruong.greenmailwebapp.utils.Constants;
import me.tinhtruong.greenmailwebapp.utils.PortUtils;

public class SetupServlet extends HttpServlet {

	private static final long serialVersionUID = 2083863593836804812L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> errorMessages = new ArrayList<String>();
		String[] protocols = req.getParameterValues("protocols");
		if (protocols == null || protocols.length == 0) {
			errorMessages.add("Please enable at least one protocol!");
		} else {
			for (String protocol : protocols) {
				int protocolPort = Integer.parseInt(req.getParameter(protocol + "Port"), 10);
				if (!PortUtils.isPortAvailable(protocolPort)) {
					errorMessages.add("Port " + protocolPort + " is not available.");
				}
			}
		}
		HttpSession session = req.getSession();
		session.setAttribute(Constants.ERROR_MESSAGES, errorMessages);
		if (errorMessages.size() == 0) {
			try {
				GreenMail greenMail = (GreenMail) session.getServletContext().getAttribute(Constants.GREENMAIL_INSTANCE_ATTRIBUTE_NAME);
				greenMail.stop();
				Thread.sleep(1500);
				List<ServerSetup> setups = new ArrayList<ServerSetup>();
				for (String protocol : protocols) {
					int protocolPort = Integer.parseInt(req.getParameter(protocol + "Port"), 10);
					setups.add(new ServerSetup(protocolPort, null, protocol));
				}
				greenMail = new GreenMail(setups.toArray(new ServerSetup[0]));
			
				greenMail.start();
				session.getServletContext().setAttribute(Constants.GREENMAIL_INSTANCE_ATTRIBUTE_NAME, greenMail);
				session.setAttribute(Constants.MESSAGE, "Restart successfully");
			} catch (Exception e) {
				errorMessages.add(e.getMessage());
			}
		}
		String contextPath = getServletContext().getContextPath();
		resp.sendRedirect(String.format("%s/?%s=%s", contextPath, Constants.PAGE_NAME_PARAM, Constants.SETUP_PAGE));
	}
}
