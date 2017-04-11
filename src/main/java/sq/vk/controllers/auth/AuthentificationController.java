package sq.vk.controllers.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Vadzim_Kavalkou on 4/11/2017.
 */
@RestController
@RequestMapping("/")
public class AuthentificationController {

  private static final Logger LOG = LoggerFactory.getLogger(AuthentificationController.class);

 /* @GetMapping(value = "/login")
  public void login(HttpServletResponse response) throws IOException {

    LOG.info("Logging in...");

    final boolean isAnonymous = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;

    if (!isAnonymous) {

      response.setStatus(308);
      response.sendRedirect("http://www.localhost:8080/client/profile");
    }

  }*/
}
