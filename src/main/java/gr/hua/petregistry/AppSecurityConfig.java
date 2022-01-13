package gr.hua.petregistry;

import javax.sql.DataSource;

import gr.hua.petregistry.auth.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public MyUserDetailsService myUserDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
        web.ignoring().antMatchers("/api/**");
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
//                .cors().and()
                .authorizeRequests()
                .antMatchers( "/pets**").authenticated()
                .antMatchers("/pets/{id}/review").hasAuthority("CAN_REVIEW_PET")
                .antMatchers(HttpMethod.GET, "/pets/{id}").hasAnyAuthority("CAN_VIEW_ALL_PETS", "CAN_VIEW_OWN_PETS", "CAN_VIEW_PENDING_PETS", "CAN_VIEW_APPROVED_MUNICIPALITY_PETS")
                .antMatchers(HttpMethod.DELETE, "/pets/{id}").hasAnyAuthority("CAN_DELETE_OWN_PETS", "CAN_DELETE_ANY_PET")
                .antMatchers(HttpMethod.POST, "/pets").hasAuthority("CAN_CREATE_PET")
                .and().formLogin().permitAll()
                .and().logout().permitAll();

        // o citizen mono create kai view
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
