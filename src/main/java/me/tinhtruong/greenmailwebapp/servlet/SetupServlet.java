package me.tinhtruong.greenmailwebapp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import me.tinhtruong.greenmailwebapp.utils.Constants;
import me.tinhtruong.greenmailwebapp.utils.Utils;

public class SetupServlet extends ActionServlet {

	private static final long serialVersionUID = 2083863593836804812L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> errorMessages = new ArrayList<String>();
		HttpSession session = req.getSession();
		ServletContext context = session.getServletContext();
		GreenMail greenMail = getGreenMailInstance(req);
		
		String[] protocols = req.getParameterValues("protocols");
		if (protocols == null || protocols.length == 0) {
			errorMessages.add("Please enable at least one protocol!");
		} else {
			greenMail.stop();
			for (String protocol : protocols) {
				int protocolPort = Integer.parseInt(req.getParameter(protocol + "Port"), 10);
				if (!Utils.isPortAvailable(protocolPort)) {
					errorMessages.add("Port " + protocolPort + " is not available.");
				}
			}
		}
		
		session.setAttribute(Constants.ERROR_MESSAGES, errorMessages);
		if (errorMessages.size() == 0) {
			try {
				Map<String, ServerSetup> setups = new HashMap<String, ServerSetup>();
				for (String protocol : protocols) {
					int protocolPort = Integer.parseInt(req.getParameter(protocol + "Port"), 10);
					setups.put(protocol, new ServerSetup(protocolPort, null, protocol));
				}
				greenMail = new GreenMail(setups.values().toArray(new ServerSetup[0]));
			
				greenMail.start();
				context.setAttribute(Constants.GREENMAIL_INSTANCE_ATTRIBUTE_NAME, greenMail);
				context.setAttribute(Constants.CURRENT_SERVER_SETUP_ATTRIBUTE_NAME, setups);
				session.setAttribute(Constants.MESSAGE, "Restart successfully");
			} catch (Exception e) {
				errorMessages.add(e.getMessage());
			}
		}
		String contextPath = getServletContext().getContextPath();
		resp.sendRedirect(String.format("%s/?%s=%s", contextPath, Constants.PAGE_NAME_PARAM, Constants.SETUP_PAGE));
	}
}
