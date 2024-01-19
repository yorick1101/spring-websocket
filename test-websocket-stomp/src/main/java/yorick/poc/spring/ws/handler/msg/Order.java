package yorick.poc.spring.ws.handler.msg;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order{
	
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
