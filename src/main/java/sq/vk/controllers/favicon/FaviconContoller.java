package sq.vk.controllers.favicon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vadzim_Kavalkou on 4/11/2017.
 */
@Controller
@RequestMapping("favicon.ico")
public class FaviconContoller {

  private static final Logger LOG = LoggerFactory.getLogger(FaviconContoller.class);

  @GetMapping
  public String favicon() {
    LOG.info("Get request to /favicon.ico");

    return "forward:/resources/images/favicon.ico";
  }

}
