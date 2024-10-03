package blogapp_api.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import blogapp_api.security.JwtAuthanticationEntryPoint;
import blogapp_api.security.JwtAuthanticationFillter;
import blogapp_api.service.CustomUserdetailService;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Config {

    public static final String[] PUBLIC_URLS = {
        "/api/v1/auth/**",
        "/v3/api-docs",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/webjars/**"
    };

    @Autowired
    private JwtAuthanticationEntryPoint jwtAuthEntryPoint;

    @Autowired
    private JwtAuthanticationFillter jwtAuthFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new CustomUserdetailService();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .cors().and()  // Enable CORS
            .authorizeHttpRequests().requestMatchers(PUBLIC_URLS).permitAll()
            .requestMatchers(HttpMethod.GET).permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean 
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
                      
            }
        };
    }
    
    @Bean 
    public FilterRegistrationBean crossFilter() {
    	UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
    	CorsConfiguration cro=new CorsConfiguration();
    	cro.setAllowCredentials(true);
    	cro.addAllowedOriginPattern("*");
    cro.addAllowedMethod("POST");
    cro.addAllowedMethod("GET");
    cro.addAllowedMethod("DELETE");
    cro.addAllowedMethod("PUT");
    cro.addAllowedMethod("OPTION");
   cro.addAllowedHeader("Authorization");
   cro.addAllowedHeader("Content-Type");
   cro.addAllowedHeader("Accept");
   cro.setMaxAge(3000L);
   source.registerCorsConfiguration("/**", cro);
    	FilterRegistrationBean bean= new FilterRegistrationBean(new CorsFilter(source)); 
    	return bean;
    	
    	
    }}