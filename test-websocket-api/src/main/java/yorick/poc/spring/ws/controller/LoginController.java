package yorick.poc.spring.ws.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {

	@PostMapping
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cookie cookie = new Cookie("token", UUID.randomUUID().toString());
		
		response.addCookie(cookie);
		response.sendRedirect("/index.html");
	}
}
