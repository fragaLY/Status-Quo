package sq.vk.configuration.mvc;

import java.util.Locale;

import com.fasterxml.jackson.core.JsonEncoding;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Created by Vadzim Kavalkou on 23.03.2016.
 */
@Configuration
@EnableWebMvc
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan("sq.vk.controllers")
@Import(SwaggerConfig.class)
public class ServletConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ViewResolver contentNegotiationResolver(ContentNegotiationManager cnm) {

        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();

        resolver.setContentNegotiationManager(cnm);
        resolver.setApplicationContext(applicationContext);
        resolver.setOrder(1);
        resolver.setDefaultViews(Lists.newArrayList(getJsonView()));

        return resolver;

    }

    private MappingJackson2JsonView getJsonView() {

        MappingJackson2JsonView view = new MappingJackson2JsonView();

        view.setPrettyPrint(true);
        view.setEncoding(JsonEncoding.UTF8);

        return view;

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true)
                  .favorParameter(false)
                  .ignoreAcceptHeader(true)
                  .useJaf(false)
                  .defaultContentType(MediaType.APPLICATION_JSON)
                  .parameterName("mediaType")
                  .mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setParameterNameDiscoverer(new LocalVariableTableParameterNameDiscoverer());
        return validator;

    }

    @Bean
    @Autowired
    public MethodValidationPostProcessor getValidationPostProcessor(LocalValidatorFactoryBean validator) {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validator);
        return processor;
    }

    @Bean(name = "messageSource")
    public MessageSource messageSource() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:i18n");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(86400);

        return messageSource;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {

        CookieLocaleResolver resolver = new CookieLocaleResolver();

        resolver.setDefaultLocale(new Locale("en"));
        resolver.setCookieName("localCookie");
        resolver.setCookieMaxAge(86400);

        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();

        interceptor.setParamName("locale");
        registry.addInterceptor(interceptor);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}