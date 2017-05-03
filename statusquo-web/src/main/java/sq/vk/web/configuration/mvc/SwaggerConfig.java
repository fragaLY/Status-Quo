package sq.vk.web.configuration.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Vadzim_Kavalkou on 4/20/2017.
 *
 * SpringFox configuration.
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan("sq.vk.web.controllers")
public class SwaggerConfig {

  private static final String TITLE = "Status Quo";
  private static final String DESCRIPTION = "Control your investments.";
  private static final String VERSION = "1.0 SNAPSHOT";
  private static final String TERMS = "http://swagger.io/terms/";
  private static final String LICENSE = "Apache 2.0";
  private static final String LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0.html";

  private static final String NAME = "Vadzim Kavalkou (Junior Software Developer 'EPAM Systems' BY/Mahileu)";
  private static final String URL = "https://github.com/fragaLY/Status-Quo";
  private static final String EMAIL = "fragalymlg@gmail.com";

  @Bean
  public Docket getDocket() {

    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .apiInfo(getApiInfo());

  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(TITLE, DESCRIPTION, VERSION, TERMS, getContact(), LICENSE, LICENSE_URL);
  }

  private Contact getContact() {
    return new Contact(NAME, URL, EMAIL);
  }

}
