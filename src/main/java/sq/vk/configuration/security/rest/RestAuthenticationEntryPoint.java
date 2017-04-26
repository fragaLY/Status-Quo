package sq.vk.configuration.security.rest;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 *
 * RestAuthenticationEntryPoint.class
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
    final HttpServletRequest request,
    final HttpServletResponse response,
    final AuthenticationException authException) throws IOException, ServletException {

    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }

}
