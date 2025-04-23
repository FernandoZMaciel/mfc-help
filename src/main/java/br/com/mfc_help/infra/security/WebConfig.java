package br.com.mfc_help.infra.security;

import br.com.mfc_help.infra.security.interceptors.UserAccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserAccessInterceptor userAccessInterceptor;

    @Autowired
    public WebConfig(UserAccessInterceptor userAccessInterceptor) {
        this.userAccessInterceptor = userAccessInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAccessInterceptor).addPathPatterns("/user/username/*");
    }
}
