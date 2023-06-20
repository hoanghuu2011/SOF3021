package poly.edu.shop.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import poly.edu.shop.domain.AccountRole;



@Component
public class UserInterceptor implements HandlerInterceptor {

	@Autowired
	private ServletContext context;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		HttpSession session = req.getSession();
		if (session.getAttribute("username") != null && session.getAttribute("role") == AccountRole.USER ) {
			resp.sendRedirect(context.getContextPath() + "/home");
			return false;
		}
		if(session.getAttribute("role") == null) {
			resp.sendRedirect(context.getContextPath() + "/home");
			return false;
		}
		return true;
	}


}