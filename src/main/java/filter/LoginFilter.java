package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// ★チェック対象にしたいURLパターンを指定します
@WebFilter(urlPatterns = {"/menu", "/route_search", "/weather/*", "/transport/*", "/record/*","/plan/*"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // ログイン状態の確認
        boolean isLoggedIn = (session != null && session.getAttribute("loginUser") != null);

        if (isLoggedIn) {
            // ログイン済みなら、本来行きたかったサーブレットへ通す
            chain.doFilter(request, response);
        } else {
            // 未ログインならログイン画面へ強制送還
            // ※パスはプロジェクトの構成に合わせて調整してください
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login/login.jsp");
        }
    }
}