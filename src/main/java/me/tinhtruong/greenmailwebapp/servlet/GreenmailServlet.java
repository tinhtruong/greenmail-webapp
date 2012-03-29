package me.tinhtruong.greenmailwebapp.servlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.tinhtruong.greenmailwebapp.utils.Constants;

public class GreenmailServlet extends HttpServlet {

	private static final long serialVersionUID = -3204160817472123845L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		if (isStaticResource(uri)) {
			returnStaticFile(uri, resp);
			return;
		}
		String page = req.getParameter(Constants.PAGE_NAME_PARAM);
		if (page == null || page.trim().length() == 0) {
			String contextPath = getServletContext().getContextPath();
			resp.sendRedirect(String.format("%s/?%s=%s", contextPath, Constants.PAGE_NAME_PARAM, Constants.MAIL_PAGE));
			return;
		}
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/master.jsp").forward(req, resp);
	}
	
	private void returnStaticFile(String uri, HttpServletResponse resp) throws IOException {
		String resourceRealPath = resolveUriToPhysicalFilePath(uri);
		InputStream is = new BufferedInputStream(new FileInputStream(resourceRealPath));
		OutputStream os = resp.getOutputStream();
		byte[] buffer = new byte[4096];
		int read = 0;
		while ((read = is.read(buffer)) != -1) {
			os.write(buffer, 0, read);
		}
		is.close();
		os.flush();
		os.close();
	}
	
	private String resolveUriToPhysicalFilePath(String uri) {
		String contextPath = getServletContext().getContextPath();
		String resourcePath = getServletContext().getRealPath(uri.replace(contextPath, ""));
		return resourcePath;
	}
	
	private boolean isStaticResource(String uri) {
		return uri.endsWith(".css")
				|| uri.endsWith(".js")
				|| uri.endsWith(".jpeg")
				|| uri.endsWith(".jpg")
				|| uri.endsWith(".png")
				|| uri.endsWith(".gif");
	}
}
