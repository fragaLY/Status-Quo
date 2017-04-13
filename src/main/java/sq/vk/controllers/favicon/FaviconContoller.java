package sq.vk.controllers.favicon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vadzim_Kavalkou on 4/11/2017.
 */
@Controller
@RequestMapping("favicon.ico")
public class FaviconContoller {

  @GetMapping
  public String favicon() {

    return "forward:/resources/images/favicon.ico";
  }

}
