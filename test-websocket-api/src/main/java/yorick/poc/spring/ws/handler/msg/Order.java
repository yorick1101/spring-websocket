package yorick.poc.spring.ws.handler.msg;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
import yorick.poc.spring.ws.handler.WebSocketSessionAware;

@Data
@lombok.EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Order extends WebSocketSessionAware{
	
	public enum Direnction {
        BUY, SELL
    }

	public enum Type {
        LIMIT, MARKET
    }
	
	private String symbol;
	private Type type;
	private Direnction direction;
	private BigDecimal quantity;
	
}
