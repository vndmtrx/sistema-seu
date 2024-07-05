package zip.fediverso.seu.diario_classe_v1.utils.interceptor;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.annotation.Generated;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Generated("Jacoco")
@Component
public class PageInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, 
                           @NonNull Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            String viewName = modelAndView.getViewName();
            modelAndView.addObject("paginaAtual", viewName);
            modelAndView.addObject("urlAtual", request.getRequestURI());
        }
    }
}
