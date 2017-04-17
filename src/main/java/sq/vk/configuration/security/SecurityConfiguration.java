package sq.vk.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import sq.vk.configuration.security.rest.RestAuthenticationEntryPoint;
import sq.vk.configuration.security.rest.RestAuthenticationSuccessHandler;
import sq.vk.configuration.security.rest.RestLogoutSuccessHandler;
import sq.vk.configuration.security.rest.RestUsernamePasswordAuthenticationFilter;

/**
 * Created by Vadzim Kavalkou on 13.11.2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Autowired
  private RestAuthenticationSuccessHandler authenticationSuccessHandler;

  @Autowired
  private RestLogoutSuccessHandler logoutSuccessHandler;

  @Autowired
  private ClientDetailsService clientDetailsService;

  @Autowired
  public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    auth.userDetailsService(clientDetailsService).passwordEncoder(encoder);
  }

  @Bean
  public RestUsernamePasswordAuthenticationFilter restFilter() throws Exception {
    RestUsernamePasswordAuthenticationFilter authFilter = new RestUsernamePasswordAuthenticationFilter();
    authFilter.setAuthenticationManager(authenticationManager());

    return authFilter;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {

    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();

    encodingFilter.setEncoding("UTF-8");
    encodingFilter.setForceEncoding(true);

    http.addFilterBefore(restFilter(), UsernamePasswordAuthenticationFilter.class);

    http.headers().frameOptions().disable();

    http.csrf().disable();

    http.formLogin().loginProcessingUrl("/login").successHandler(authenticationSuccessHandler);

    http.logout().logoutUrl("/logout").invalidateHttpSession(true).deleteCookies(
      "JSESSIONID").logoutSuccessHandler(logoutSuccessHandler);

    http.authorizeRequests()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers(
      "/login").fullyAuthenticated();

    http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);

  }

}