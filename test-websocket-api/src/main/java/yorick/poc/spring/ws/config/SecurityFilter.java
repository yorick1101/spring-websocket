package yorick.poc.spring.ws.config;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityFilter implements Filter{

	private String[] ignoredPath = {"/login.html","/login","/mock"};

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)

			throws IOException, ServletException {

		/**
		 * Maybe HandshakeInterceptor 
		 */
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		log.info("request id:{}, uri:{}, schema:{}", req.getRequestId(), uri, req.getScheme());

		//mock ignored path
		for (String path : ignoredPath) {
			if (uri.startsWith(path)) {
				chain.doFilter(request, response);
				return;
			}
		}


		Cookie[] cookies = req.getCookies();
		log.info("request id:{};cookie:{}", req.getRequestId(), cookies==null?"no cookie":cookies.length);
		if (cookies != null) {
			for(Cookie cookie: cookies) {
				log.info("request id:{};cookie name:{}", req.getRequestId(), cookie.getName());
				if(cookie.getName().equals("token")) {
					chain.doFilter(request, response);
					return;
				}
			}
		}
		HttpServletResponse res = (HttpServletResponse) response;
        if(req.getHeader("User-Agent")!=null && req.getHeader("User-Agent").startsWith("PostmanRuntime")) {
        	res.setStatus(401);
        }else {
        	res.sendRedirect("/login.html");
        }
		
	}

}
