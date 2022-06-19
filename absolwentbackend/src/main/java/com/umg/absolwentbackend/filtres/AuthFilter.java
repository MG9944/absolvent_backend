package com.umg.absolwentbackend.filtres;

import com.umg.absolwentbackend.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Pozyskiwanie tokena....");
        HttpServletRequest httpRequest =  request;
        HttpServletResponse httpResponse = response;

        String authHeader = httpRequest.getHeader("Authorization");
        if(authHeader != null)
        {
            String[] authHeaderArr = authHeader.split("Bearer ");
            if(authHeaderArr.length > 1 && authHeaderArr[1] != null) {
                String token = authHeaderArr[1];
                try {
                    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Constants.API_SECRET_KEY));
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(token).getBody();
                    httpRequest.setAttribute("login",claims.get("login"));
                    httpRequest.setAttribute("admin", Boolean.parseBoolean(claims.get("admin").toString()));
                }catch (Exception e) {
                    System.out.println("expired");
                    StringBuilder sb = new StringBuilder();
                    sb.append("{ ");
                    sb.append("\"error\": \"true\",");
                    sb.append("\"message\": \"Token Expired.\",");
                    sb.append("} ");
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(sb.toString());
                    return;
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("{ ");
                sb.append("\"error\": \"true\",");
                sb.append("\"message\": \"Authorization token must be Bearer [token]\",");
                sb.append("} ");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(sb.toString());
                return;
            }
        }
        else
        {
            System.out.println("TEST");
            StringBuilder sb = new StringBuilder();
            sb.append("{ ");
            sb.append("\"error\": \"true\",");
            sb.append("\"message\": \"Authorization token must be provided\",");
            sb.append("} ");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(sb.toString());
            return;
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        System.out.println("shouldNotFilter token....");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("/api/admin/password/reset", true);
        map.put("/api/auth/admin", true);
        map.put("/api/auth/pool",true);
        map.put("/api/public",true);
        String path = request.getRequestURI();
        return map.containsKey(path);
    }
}
