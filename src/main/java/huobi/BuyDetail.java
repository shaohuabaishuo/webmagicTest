package huobi;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class BuyDetail {

    /**
     * 买入人民币
     */
    private BigDecimal price;

    /**
     * ustd
     */
    private BigDecimal usdt;

    /**
     * usdt数量
     */
    private BigDecimal count;



    public BuyDetail() {
    }

    public BuyDetail(BigDecimal usdt, BigDecimal count) {
        this.usdt = usdt;
        this.count = count;
    }

    public BuyDetail(BigDecimal usdt) {
        this.usdt = usdt;
    }

    //
//    public BuyDetail(BigDecimal price) {
//        this.price = price;
//    }
//
//    private void setUsdt(BigDecimal usdt) {
//        this.usdt = price.divide(new BigDecimal("6.44"));
//    }
}
