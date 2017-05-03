package sq.vk.web.configuration.security.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.Assert;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 *
 * RestUsernamePasswordAuthenticationFilter.class
 */
public class RestUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
  private static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";

  private boolean postOnly = true;

  private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
  private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

  public RestUsernamePasswordAuthenticationFilter() {
    super("/j_spring_security_check");
  }

  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    if (postOnly && !request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
    }

    String username = obtainUsername(request);
    String password = obtainPassword(request);

    if (username == null) {
      username = "";
    }

    if (password == null) {
      password = "";
    }

    username = username.trim();

    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

    setDetails(request, authRequest);

    return this.getAuthenticationManager().authenticate(authRequest);
  }

  private String obtainPassword(HttpServletRequest request) {
    return request.getParameter(passwordParameter);
  }


  private String obtainUsername(HttpServletRequest request) {
    return request.getParameter(usernameParameter);
  }

  private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
    authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
  }

  public void setUsernameParameter(String usernameParameter) {
    Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
    this.usernameParameter = usernameParameter;
  }

  public void setPasswordParameter(String passwordParameter) {
    Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
    this.passwordParameter = passwordParameter;
  }

  public void setPostOnly(boolean postOnly) {
    this.postOnly = postOnly;
  }

  public final String getUsernameParameter() {
    return usernameParameter;
  }

  public final String getPasswordParameter() {
    return passwordParameter;
  }

}
