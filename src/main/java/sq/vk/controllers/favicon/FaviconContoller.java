package sq.vk.controllers.favicon;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sq.vk.controllers.exceptionhandlers.errordetails.ErrorDetails;
import sq.vk.core.dto.statistic.StatisticDto;

/**
 * Created by Vadzim_Kavalkou on 4/11/2017.
 */
@Controller
@Api(value = "favicon", description = "Favicon API")
@RequestMapping("favicon.ico")
public class FaviconContoller {

  private static final String FORWARD_FAVICON = "forward:/resources/images/favicon.ico";

  @GetMapping
  @ApiOperation(value = "Retrieves favicon",
      notes = "The path of favicon will be sent in the location response",
      response = StatisticDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "The path of favicon was retrieved", response = String.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 404, message = "The path of favicon was not found", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error getting the path of favicon", response = ErrorDetails.class)} )
  public String getFavicon() {

    return FORWARD_FAVICON;

  }

}
