package ex3.ex3.config;

import ex3.ex3.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //spring security를 사용하는데 있어서 로그인은 UserDetailService를 통해서 정보를 가져온다.
    //MemberService는 UserDetailService를 구현할 것이다.
    private final MemberService memberService;

    //서비스에서 비밀번호를 암호화하는 이 메서드를 사용할것이기에 빈으로 등록해놓음. (암호는 암호화돼서 DB에 저장되도록 할것이기 때문에)
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // http 권한 관련 설정 메서드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN") // /admin 경로 자원들 ADMIN권한만 접근가능
                    .antMatchers("/user/info").hasRole("USER") // /user/info 자원에는 USER권한만 접근가능
                    .antMatchers("/**").permitAll() //나머진 누구든 접근가능
                .and()
                    .formLogin()
                    .loginPage("/user/login") // custom login - 설정하지않으면 default 로 로그인 페이지가 /login임.
                    .defaultSuccessUrl("/user/login/result") //로그인 성공하면 이 url로 redirect
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // custom logout - 설정하지 않으면 default로 로그아웃 페이지가 /logout임.
                    .logoutSuccessUrl("/user/logout/result") // 로그아웃 성공시 redirect
                    .invalidateHttpSession(true) // 로그아웃 성공시 session 모두제거
                .and()
                    .exceptionHandling().accessDeniedPage("/user/denied"); // 권한 거부된 페이지로 redirect 되게 하기위해서.
    }

    // 로그인 처리를 위한 메서드라 생각하자!(memberService는 UserDeatilService를 구현했기에 MemberService에는 loadUserByUsername()이라는 메서드가 구현되어있을것이다. 이 메서드가 꼭 구현되어야한다.)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
