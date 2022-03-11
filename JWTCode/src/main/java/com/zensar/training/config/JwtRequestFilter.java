package com.zensar.training.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zensar.training.security.util.JwtUtil;
import com.zensar.training.service.LoginServiceImpl;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	LoginServiceImpl loginServiceImpl;
	
	@Autowired
	JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader=request.getHeader("Authorization");
		
		String userName=null;
		String jwtStr=null;
		
		if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) {
			jwtStr=authorizationHeader.substring(7);
			userName=jwtUtil.extractUserName(jwtStr);
		}
		
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails=loginServiceImpl.loadUserByUsername(userName);
			if(jwtUtil.validateToken(jwtStr, userDetails)) {
				UsernamePasswordAuthenticationToken uptoken;
				uptoken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				uptoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(uptoken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
