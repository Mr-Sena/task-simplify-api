package br.com.programme.sistematarefassimplify.infra.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.programme.sistematarefassimplify.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {


    @Autowired
    UsuarioService service;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if(servletPath.equals("/auth")) {

            filterChain.doFilter(request, response);
            var authorization = request.getHeader("Authorization");

            var authEnconded = authorization.substring("Basic".length()).trim(); //N29hbzpjbGllbnQtcHdk
            byte[] authDecodeStream = Base64.getDecoder().decode(authEnconded); // --> [B@86d43d98

            var authString = new String(authDecodeStream); // --> username:password

            String[] credential = authString.split(":");
            String username = credential[0];
            String password = credential[1];

            //Validar usuário:
            var usuario = service.findUser(username);
            if (usuario == null) {
                response.sendError(401, "Não foi possível encontrar o usuário.");
            } else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), usuario.getPassword());
                if(passwordVerify.verified) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "Não foi possível verificar o login.");
                }
            }

        } else {
            filterChain.doFilter(request, response);
        }

    }
}
