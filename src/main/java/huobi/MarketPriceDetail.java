package huobi;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class MarketPriceDetail {
    /**
     * 该对象的交易代码
     */
    private String symbol;
    /**
     * 当前价格
     */
    private BigDecimal bid;
    /**
     * 开盘价
     */
    private BigDecimal open;
    /**
     * 当日最高价
     */
    private BigDecimal high;
    /**
     * 当日最低价
     */
    private BigDecimal low;
}
