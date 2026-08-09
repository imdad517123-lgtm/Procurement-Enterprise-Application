package Procurement.Master.Security;




import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {


    private final String SECRET_KEY =
            "myverysecretkeymyverysecretkey123456789";


    private SecretKey getSigningKey(){

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );

    }



    public String generateToken(
            Long employeeId,
            String email,
            String role){


        return Jwts.builder()

                .setSubject(email)

                .claim(
                    "employeeId",
                    employeeId
                )

                .claim(
                    "role",
                    role
                )

                .setIssuedAt(
                    new Date()
                )

                .setExpiration(
                    new Date(
                    System.currentTimeMillis()
                    +1000*60*60*10
                    )
                )

                .signWith(
                    getSigningKey(),
                    SignatureAlgorithm.HS256
                )

                .compact();

    }





    public Claims extractClaims(String token){


        return Jwts.parserBuilder()

                .setSigningKey(
                    getSigningKey()
                )

                .build()

                .parseClaimsJws(token)

                .getBody();

    }




    public String extractEmail(String token){

        return extractClaims(token)
                .getSubject();

    }


}