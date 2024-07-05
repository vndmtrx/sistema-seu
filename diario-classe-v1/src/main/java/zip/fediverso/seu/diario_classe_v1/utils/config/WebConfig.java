package zip.fediverso.seu.diario_classe_v1.utils.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.Generated;

@Generated("Jacoco")
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private zip.fediverso.seu.diario_classe_v1.utils.interceptor.PageInterceptor pageInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(pageInterceptor);
    }
}