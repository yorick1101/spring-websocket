package yorick.poc.spring.ws.handler;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionRegisteredTextWebSocketHandler extends TextWebSocketHandler {
	
	private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.error("Error on session {}", session.getId(), exception);
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.put(session.getId(), session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session.getId());
	}
	
    Collection<WebSocketSession> getSessions(){
		return sessions.values();
	}

}
