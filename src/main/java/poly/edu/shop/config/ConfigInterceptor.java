package poly.edu.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import poly.edu.shop.interceptor.AuthenticationInterceptor;

import poly.edu.shop.interceptor.UserInterceptor;



@Configuration
public class ConfigInterceptor implements WebMvcConfigurer{
@Autowired
private AuthenticationInterceptor authenticationInterceptor;

@Autowired
private UserInterceptor userInterceptor;



 @Override
 public void addInterceptors(InterceptorRegistry registry) {
	
	 registry.addInterceptor(authenticationInterceptor)
		.addPathPatterns("/admin/**");
	 
	registry.addInterceptor(userInterceptor)
		.addPathPatterns("/admin/**");
 }

}
