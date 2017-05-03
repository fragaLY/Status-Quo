package sq.vk.web.configuration.security;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import sq.vk.web.configuration.security.rest.RestAuthenticationEntryPoint;
import sq.vk.web.configuration.security.rest.RestAuthenticationSuccessHandler;
import sq.vk.web.configuration.security.rest.RestLogoutSuccessHandler;

/**
 * Created by Vadzim Kavalkou on 13.11.2016.
 *
 * Security configuration.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Inject
  private RestAuthenticationEntryPoint authenticationEntryPoint;

  @Inject
  private RestAuthenticationSuccessHandler authenticationSuccessHandler;

  @Inject
  private RestLogoutSuccessHandler logoutSuccessHandler;

  @Inject
  private ClientDetailsService clientDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {

    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(clientDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(clientDetailsService);
    auth.authenticationProvider(authProvider());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {

    //http.addFilterBefore(restFilter(), UsernamePasswordAuthenticationFilter.class);

    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();

    encodingFilter.setEncoding("UTF-8");
    encodingFilter.setForceEncoding(true);

    http
          .headers().frameOptions().disable()
        .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and()
        .addFilterBefore(encodingFilter, CsrfFilter.class)
        .authorizeRequests()
          .antMatchers("/swagger-ui/**", "/v2/api-docs", "/favicon.ico", "/login").permitAll()
          .antMatchers("/statistics/**", "/clients/**", "/logout").authenticated()
        .and()
          .formLogin().successHandler(authenticationSuccessHandler).defaultSuccessUrl("/clients/profile")
        .and()
          .logout().logoutUrl("/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID")
          .logoutSuccessHandler(logoutSuccessHandler)
        .and()
          .httpBasic()
          .authenticationEntryPoint(authenticationEntryPoint)
          .realmName("Status Quo")
        .and()
          .csrf()
          .disable();

  }

}