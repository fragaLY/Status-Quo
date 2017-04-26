package sq.vk.configuration.security.rest;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 *
 * RestAuthenticationSuccessHandler.class
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private RequestCache requestCache = new HttpSessionRequestCache();

  private static final Logger LOG = LoggerFactory.getLogger(RestAuthenticationSuccessHandler.class);

  @Override
  public void onAuthenticationSuccess(
    HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication) throws IOException, ServletException {

    LOG.info("On auth success handler: [ {} ], [ {} ] , [ {} ].", request, response, authentication);

    final SavedRequest savedRequest = requestCache.getRequest(request, response);

    if (savedRequest == null) {

      clearAuthenticationAttributes(request);
      return;
    }

    String targetUrlParam = getTargetUrlParameter();

    if (isAlwaysUseDefaultTargetUrl()| (targetUrlParam != null && StringUtils.hasText(request.getParameter(targetUrlParam)))) {

      requestCache.removeRequest(request, response);

      clearAuthenticationAttributes(request);
      return;
    }

    clearAuthenticationAttributes(request);

  }

  public void setRequestCache(RequestCache requestCache) {
    this.requestCache = requestCache;
  }

}
