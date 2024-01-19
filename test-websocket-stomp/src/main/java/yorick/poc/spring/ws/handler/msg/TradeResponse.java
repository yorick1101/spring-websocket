package yorick.poc.spring.ws.handler.msg;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeResponse {

	
	private boolean success;
	private TradeRequest request;
}
