package yorick.poc.spring.ws.handler.msg;

import java.math.BigDecimal;

import lombok.Data;

@Data	
public class TradeRequest {

	private String tradeId = "1";
	private String symbol;
	private BuySell buySell;
	private BigDecimal price;
	private BigDecimal quantity;
}
