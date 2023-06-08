/**
 * 
 */
/**
 * @author ilham.zannuary
 *
 */
package com.miniproject.interviewcode.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.InitBinderDataBinderFactory;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import com.miniproject.interviewcode.auth.sercurity.CustomAccessDeniedHandler;
import com.miniproject.interviewcode.auth.sercurity.CustomUserDetailsService;
import com.miniproject.interviewcode.auth.sercurity.JwtAuthenticationEntryPoint;
import com.miniproject.interviewcode.auth.sercurity.JwtAuthenticationFilter;




@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	
	@Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

   
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    private static final String[] AUTH_LIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
    	return (web) ->
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		  //Cross origin resource sharing
		 http
		 .csrf().disable()
         .sessionManagement(session -> session
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .antMatcher("/library/**")
         .authorizeRequests()
         .antMatchers(AUTH_LIST)
      	.permitAll()
          .antMatchers("/public-api/**")
              .permitAll()
          .antMatchers(HttpMethod.GET,"/public-api/**","/users/**","/listings","/create/listings")
              .permitAll()
            .antMatchers(HttpMethod.POST,"/public-api/**","/users/**","/listings","/create/listings")
         .authenticated()
         .and()
         .httpBasic()
         .and()
         .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
         .and().logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
         .and()
         .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
         .exceptionHandling(exception -> exception
                 .authenticationEntryPoint(unauthorizedHandler)
                 .accessDeniedHandler( new CustomAccessDeniedHandler()));

 return http.build();
 
 
	}

	

	@Bean
	public WebMvcConfigurer corsConfigure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO Auto-generated method stub
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");

			}
		};

	}
	


	@Bean
	public WebMvcRegistrations mvcRegistrations() {
		return new WebMvcRegistrations() {
			@Override
			public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
				return new ExtendedRequestMappingHandlerAdapter();
			}
		};
	}


	private static class ExtendedRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

		@Override
		protected InitBinderDataBinderFactory createDataBinderFactory(List<InvocableHandlerMethod> methods) {

			return new ServletRequestDataBinderFactory(methods, getWebBindingInitializer()) {

				@Override
				protected ServletRequestDataBinder createBinderInstance(
						Object target, String name, NativeWebRequest request) throws Exception {
					
					ServletRequestDataBinder binder = super.createBinderInstance(target, name, request);
					String[] fields = binder.getDisallowedFields();
					List<String> fieldList = new ArrayList<>(fields != null ? Arrays.asList(fields) : Collections.emptyList());
					fieldList.addAll(Arrays.asList("class.*", "Class.*", "*.class.*", "*.Class.*"));
					binder.setDisallowedFields(fieldList.toArray(new String[] {}));
					return binder;
				}
			};
		}
	}

}