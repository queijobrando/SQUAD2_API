package com.example.squad2_suporte.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities().isEmpty()) {
            return true; // deixa passar para endpoints públicos
        }

        String role = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        String municipioToken = (String) auth.getDetails();

        // ADMIN tem acesso total
        if (role.equals("ADMIN")) {
            return true;
        }

        // VIGILANCIA só pode fazer GET
        if (role.equals("VIGILANCIA") && !request.getMethod().equals("GET")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Usuário VIGILANCIA só pode realizar requisições GET.");
            return false;
        }

        // MUNICIPAL só pode acessar seu próprio município
        if (role.equals("MUNICIPAL")) {
            String municipioRequest = request.getParameter("municipio");
            if (municipioRequest != null && !municipioRequest.equalsIgnoreCase(municipioToken)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Usuário MUNICIPAL só pode acessar dados do próprio município.");
                return false;
            }
        }

        return true;
    }
}
