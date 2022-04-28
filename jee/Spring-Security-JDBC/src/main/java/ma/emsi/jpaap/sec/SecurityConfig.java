package ma.emsi.jpaap.sec;

import ma.emsi.jpaap.sec.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Pour specifier la page login
        //http.formLogin().loginPage("/login");
        http.formLogin();
        // Les Droits d'acces
        // Check Endpoint for authorization
        http.authorizeRequests().antMatchers("/home").permitAll(); // Authorize Everyone
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");
        // Tout les requetes necessite l'authentification
        http.authorizeRequests().anyRequest().authenticated();

        // Configurer les Exception
        http.exceptionHandling().accessDeniedPage("/403");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        PasswordEncoder passwordEncoder = passwordEncoder();
        /*//
         In Memory Auth
         Les Utilisateur seront stocké en memoire
        // {noop} No password Encoder
        PasswordEncoder passwordEncoder = passwordEncoder();
        String encodedPWD = passwordEncoder.encode("1234");
        auth.inMemoryAuthentication()
                .withUser("nassim").password(encodedPWD).roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("12345")).roles("USER","ADMIN");
*/

        // JDBC Auth Using MYSQL

        // Principal means username in Spring
        // Credentials means password


      /*  auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal,password as credentials,active from users where username = ?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username = ?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);*/


        // User details Authentication
        auth.userDetailsService(userDetailsService);
    }

    // Creates Bcrypt Password Encoder
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
