package sq.vk.web.configuration.security.rest;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 *
 * RestLogoutSuccessHandler.class
 */
@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

  private static final Logger LOG = LoggerFactory.getLogger(RestLogoutSuccessHandler.class);

  @Override
  public void onLogoutSuccess(
    HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication) throws IOException, ServletException {

    LOG.info("Log out success handler.");

    super.onLogoutSuccess(request, response, authentication);

    response.setStatus(HttpServletResponse.SC_OK);
  }

}
