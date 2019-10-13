package com.music.feed

import java.io.IOException
import java.util.stream.Collectors

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException


class JWTAuthorizationFilter : OncePerRequestFilter() {

    private val HEADER = "Authorization"
    private val PREFIX = "Bearer "
    private val SECRET = "mySecretKey"

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            if (existingJWTToken(request, response)) {
                val claims = validateToken(request)
                if (claims["authorities"] != null) {
                    setUpSpringAuthentication(claims)
                } else {
                    SecurityContextHolder.clearContext()
                }
            }
            chain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.message)
            return
        } catch (e: UnsupportedJwtException) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.sendError(HttpServletResponse.SC_FORBIDDEN,  e.message)
            return
        } catch (e: MalformedJwtException) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.sendError(HttpServletResponse.SC_FORBIDDEN,  e.message)
            return
        }

    }

    private fun validateToken(request: HttpServletRequest): Claims {
        val jwtToken = request.getHeader(HEADER).replace(PREFIX, "")
        return Jwts.parser().setSigningKey(SECRET.toByteArray()).parseClaimsJws(jwtToken).body
    }

    /**
     * Metodo para autenticarnos dentro del flujo de Spring
     *
     */
    private fun setUpSpringAuthentication(claims: Claims) {
        val authorities = claims["authorities"] as List<*>

        val auth = UsernamePasswordAuthenticationToken(claims.subject, null,
                authorities.stream().map
                {SimpleGrantedAuthority(it.toString()) }
                        .collect(Collectors.toList<SimpleGrantedAuthority>()))
        SecurityContextHolder.getContext().authentication = auth

    }

    private fun existingJWTToken(request: HttpServletRequest, res: HttpServletResponse): Boolean {
        val authenticationHeader = request.getHeader(HEADER)
        return !(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
    }

}