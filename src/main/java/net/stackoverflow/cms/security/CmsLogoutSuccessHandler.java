package net.stackoverflow.cms.security;

import lombok.extern.slf4j.Slf4j;
import net.stackoverflow.cms.common.Result;
import net.stackoverflow.cms.util.JsonUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 注销成功handler
 *
 * @author 凉衫薄
 */
@Slf4j
public class CmsLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) request.getSession().getAttribute("user");
        log.info(userDetails.getUsername() + ":注销登录成功");

        Result result = new Result();
        result.setStatus(Result.Status.SUCCESS);
        result.setMessage("注销登录成功");
        PrintWriter out = response.getWriter();
        out.write(JsonUtils.bean2json(result));
        out.flush();
        out.close();
    }
}
