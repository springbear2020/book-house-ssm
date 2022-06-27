package edu.whut.bear.panda.interceptor;

import edu.whut.bear.panda.exception.LoginInterceptorException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Spring-_-Bear
 * @datetime 6/6/2022 6:57 PM
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Check the login state of the user
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user == null) {
            throw new LoginInterceptorException("亲爱的用户，请先登录您的账号");
        }
        return true;
    }
}
