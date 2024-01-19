package yorick.poc.spring.ws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSoketRelayConfig implements WebSocketMessageBrokerConfigurer {
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket").setHandshakeHandler(new DefaultHandshakeHandler());
	}
	
	
	/**
	 * queue and topic are supported by rabbitmq
	 * https://www.rabbitmq.com/stomp.html#d
	 */
	@Override	
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableStompBrokerRelay("/topic", "/queue").setRelayHost("localhost").setRelayPort(61613)
		.setClientLogin("guest").setClientPasscode("guest")  
		.setSystemLogin("guest").setSystemPasscode("guest"); //guest/guest is the default for both accounts


		/**
		 * Configure one or more prefixes to filter destinations targeting application
		 * annotated methods. For example destinations prefixed with "/app" may be
		 * processed by annotated methods while other destinations may target the
		 * message broker (e.g. "/topic", "/queue").
		 * <p>When messages are processed, the matching prefix is removed from the destination
		 * in order to form the lookup path. This means annotations should not contain the
		 * destination prefix.
		 * <p>Prefixes that do not have a trailing slash will have one automatically appended.
		 */
		registry.setApplicationDestinationPrefixes("/app");  
	}
}
