package com.astrider.sfc.src.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.lib.FlashMessage;
import com.astrider.sfc.src.model.vo.db.UserVo;
import static com.astrider.sfc.ApplicationContext.*;

/**
 * @author Itsuki Sakitsu /User以下接続時にログイン済みか否かを確認する
 */
public class LoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		try {
			HttpSession session = ((HttpServletRequest) request).getSession();

			UserVo user = (UserVo) session.getAttribute(SESSION_USER);
			if (user == null) {
				FlashMessage flashMessage = new FlashMessage();
				flashMessage.addMessage("指定されたURLに接続するにはログインが必要です");
				session.setAttribute(SESSION_FLASH_MESSAGE, flashMessage);

				String target = ((HttpServletRequest) request).getContextPath() + PAGE_UNKNOWN;
				((HttpServletResponse) response).sendRedirect(target);
			} else {
				chain.doFilter(request, response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
