package yorick.poc.spring.ws.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import yorick.poc.spring.ws.handler.msg.MarketOrder;
import yorick.poc.spring.ws.handler.msg.Order;
import yorick.poc.spring.ws.handler.msg.Quotation;

@Slf4j
public class TradeWebSocketHandler extends SessionRegisteredTextWebSocketHandler {
	
	@Autowired
	private ObjectMapper objectMapper;
	

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		
		Order order;
		try {
			order = objectMapper.readValue(message.getPayload(), MarketOrder.class);
			order.setSessionId(session.getId());
			log.info("Received message: " + order);
		} catch (JsonProcessingException  e) {
			// TODO Auto-generated catch block
			session.sendMessage(new TextMessage("message format is incorrect" + message.getPayload()));
			e.printStackTrace();
		} 
	}

}
