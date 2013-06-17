package com.astrider.sfc.app.filter;

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

import com.astrider.sfc.app.model.vo.db.UserVo;
import com.astrider.sfc.lib.helper.FlashMessage;

public class LoginFilter implements Filter {
    private static final String UNKNOWN_PATH = "/Unknown";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        try {
            HttpSession session = ((HttpServletRequest) request).getSession();

            UserVo user = (UserVo) session.getAttribute("loginUser");
            if (user == null) {
                FlashMessage flashMessage = new FlashMessage();
                flashMessage.addMessage("指定されたURLに接続するにはログインが必要です");
                session.setAttribute("flashMessage", flashMessage);
                String target = ((HttpServletRequest) request).getContextPath() + UNKNOWN_PATH;
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
