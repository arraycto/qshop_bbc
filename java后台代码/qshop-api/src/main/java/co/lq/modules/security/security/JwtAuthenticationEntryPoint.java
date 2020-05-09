package co.lq.modules.security.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author billy
 * @date 2020/01/12
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        //将调用此方法发送401 响应
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                authException == null ? "Unauthorized" : authException.getMessage());
    }
}
