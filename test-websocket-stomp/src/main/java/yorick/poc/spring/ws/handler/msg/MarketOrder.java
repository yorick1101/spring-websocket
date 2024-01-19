package yorick.poc.spring.ws.handler.msg;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true)
public class MarketOrder extends Order{

	@Override
	public final Type getType() {
		return Type.MARKET;
	}
	
	private BigDecimal price;
	
}
