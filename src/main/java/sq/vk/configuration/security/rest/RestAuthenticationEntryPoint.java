package sq.vk.configuration.security.rest;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger LOG = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

  @Override
  public void commence(
    final HttpServletRequest request,
    final HttpServletResponse response,
    final AuthenticationException authException) throws IOException, ServletException {

    LOG.info("AuthenticationEntryPoint: [ {} ], [ {} ], [ {} ]",request , response, authException);

    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }

}
