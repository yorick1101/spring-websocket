package yorick.poc.spring.ws.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import lombok.extern.slf4j.Slf4j;
import yorick.poc.spring.ws.handler.QuoteWebSocketHandler;
import yorick.poc.spring.ws.handler.TradeWebSocketHandler;

@Profile("websocket")
@EnableWebSocket
@Configuration
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(handler(), "/trade")
		.addHandler(quoteHandler(), "/quote")
		.addInterceptors(new HandshakeInterceptor() {

			@Override
			public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
					WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
				log.info("before customized Handshake");
				return true;
			}

			@Override
			public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
					WebSocketHandler wsHandler, Exception exception) {
				log.info("after customized Handshake");
				
			}
			
		})
		.addInterceptors(new HttpSessionHandshakeInterceptor())
		;
	}

	@Bean
	public TradeWebSocketHandler handler() {
		return new TradeWebSocketHandler();
	}
	
	@Bean 
	public QuoteWebSocketHandler quoteHandler() {
		return new QuoteWebSocketHandler();
	}
	
}
