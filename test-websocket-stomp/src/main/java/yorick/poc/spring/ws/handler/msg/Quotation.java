package yorick.poc.spring.ws.handler.msg;

import java.math.BigDecimal;

public record Quotation(String symbol, BigDecimal buy, BigDecimal sell, BigDecimal last, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, long quoteTime) {

}
