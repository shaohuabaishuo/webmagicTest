package huobi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class HuobiPageProcessor implements PageProcessor {
    /**
     * 购买的价钱，购买的
     */
    private List<BuyDetail> inList;

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        JSONObject jsonObject = JSONObject.parseObject(page.getRawText());
        String data = jsonObject.getString("data");
//        System.out.println(data);
        List<MarketPriceDetail> marketPriceDetailList = JSONArray.parseArray(jsonObject.getString("data"), MarketPriceDetail.class);
        MarketPriceDetail marketPriceDetail1 = marketPriceDetailList.stream().filter(item -> item.getSymbol().equals("ethusdt")).collect(Collectors.toList()).get(0);
        System.out.println(marketPriceDetail1.getBid());


        BigDecimal in = new BigDecimal(0);
        for (int i = 0; i < inList.size(); i++) {
            BigDecimal multiply = marketPriceDetail1.getBid()
                    .subtract(inList.get(i).getUsdt())
                    .multiply(new BigDecimal("6.44"))
                    .multiply(inList.get(i).getCount());
            in = in.add(multiply);
        }
//        System.out.println("输出:"+in);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
