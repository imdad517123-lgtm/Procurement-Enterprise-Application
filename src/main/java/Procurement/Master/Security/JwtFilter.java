package Procurement.Master.Security;



import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    private JwtService jwtService;



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws IOException, ServletException {



        String header = request.getHeader("Authorization");


        if(header != null && header.startsWith("Bearer ")) {


            try {


                String token = header.substring(7);


                String email =
                        jwtService.extractEmail(token);



                if(email != null &&
                   SecurityContextHolder
                   .getContext()
                   .getAuthentication() == null) {



                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    List.of()
                            );


                    SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);


                }


            }
            catch(Exception e) {

                System.out.println(
                    "JWT ERROR : " + e.getMessage()
                );

            }


        }


        filterChain.doFilter(
                request,
                response
        );

    }

}