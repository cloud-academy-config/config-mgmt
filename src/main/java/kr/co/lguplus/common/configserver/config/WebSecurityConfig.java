package kr.co.lguplus.common.configserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // http 시큐리티 빌더
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/actuator/**").permitAll()
        .antMatchers("/**").authenticated()
        .and()
        .httpBasic();

    // 세션정보 저장 안함.
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }


  @Bean
  public static NoPasswordEncoder passwordEncoder() {
    return (NoPasswordEncoder) NoPasswordEncoder.getInstance();
  }

  private final static class NoPasswordEncoder implements PasswordEncoder {

    private static final PasswordEncoder INSTANCE = new NoPasswordEncoder();

    private NoPasswordEncoder() {
    }

    @Override
    public String encode(CharSequence rawPassword) {
      return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
      return rawPassword.toString().equals(encodedPassword);
    }

    public static PasswordEncoder getInstance() {
      return INSTANCE;
    }
  }

}