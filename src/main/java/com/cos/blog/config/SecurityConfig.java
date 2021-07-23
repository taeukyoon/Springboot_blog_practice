package com.cos.blog.config;


import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는것

@Configuration //빈등록
@EnableWebSecurity //시큐리티 필터로 등록 (설정)
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근 하면 권한 및 인증을 미리 체크한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean //Ioc가 된다
    public BCryptPasswordEncoder encoderPWD() {
        return new BCryptPasswordEncoder();
    }
    /*
     시큐리티가 로그인을 할때 password를 가로채기해서 해당 password가 뭐로 해쉬화가 되어
     회원가입이 되었는지 알아야하기 때문에 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있다.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf토큰 비활성화
                .authorizeRequests()
                .antMatchers("/", "/auth/**","/js/**","/css/**","/image/**","/dummy/**")
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc")
                .defaultSuccessUrl("/"); //스프링시큐리티가 해당주소로 로그인을 가로채서 대신 로그인을 한다.
    }
}
