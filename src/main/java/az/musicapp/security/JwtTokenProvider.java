package az.musicapp.security;

import az.musicapp.domain.Role;
import az.musicapp.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static az.musicapp.security.SecurityConstants.EXPIRATION_TIME;


@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private  String JWT_SECRET;

    @Autowired
    private UserDetailsService userDetailsService;

    protected final Log logger = LogFactory.getLog(this.getClass());


    @PostConstruct
    protected void init() {

        JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
    }


    public String createToken(String username, List<Role> roles) {

        Claims claims = Jwts.claims().setSubject(username);
//        claims.put("roles", getRoleNames(roles));

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(expiryDate)//
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)//
                .compact();
    }


    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String jwtToken) {

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException ex) {
            throw new JwtAuthenticationException("Invalid JWT Signature");

        } catch (MalformedJwtException ex) {
            throw new JwtAuthenticationException("Invalid JWT Token");

        } catch (ExpiredJwtException ex) {
            throw new JwtAuthenticationException("Expired JWT token");

        } catch (UnsupportedJwtException ex) {
            throw new JwtAuthenticationException("Unsupported JWT token");

        } catch (IllegalArgumentException ex) {
            throw new JwtAuthenticationException("JWT claims string is empty");

        }

    }

}
