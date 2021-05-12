package jobhunter.employerservice.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthTokenHTTPInterceptorConfig implements WebMvcConfigurer {
    private final BearerExtractor bearerExtractor;

    public AuthTokenHTTPInterceptorConfig(BearerExtractor bearerExtractor) {
        this.bearerExtractor = bearerExtractor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(new AuthTokenHTTPInterceptor(bearerExtractor))
                .addPathPatterns("/create")
                .addPathPatterns("/update")
                .addPathPatterns("/acceptApplication/*/*")
                .addPathPatterns("/rejectApplication/*/*")
                .addPathPatterns("/completeJob/*");
    }
}