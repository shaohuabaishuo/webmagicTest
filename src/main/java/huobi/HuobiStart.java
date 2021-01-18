package huobi;

import fund.FundAnalysisPageProcessor;
import fund.FundAnalysisPipeline;
import us.codecraft.webmagic.Spider;

import java.math.BigDecimal;
import java.util.Arrays;

public class HuobiStart {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            Thread.sleep(1000);
            HuobiPageProcessor pageProcessor = new HuobiPageProcessor();
            pageProcessor.setInList(Arrays.asList(new BuyDetail(new BigDecimal("1039"), new BigDecimal("549.37398")), new BuyDetail(new BigDecimal("1043"),
                    new BigDecimal("366.614368")), new BuyDetail(new BigDecimal("1015"), new BigDecimal("308.239008"))));
            HuobiPipeline pipeline = new HuobiPipeline();
            Spider spider = Spider.create(pageProcessor);
//        spider.addUrl("https://fundmobapi.eastmoney.com/FundMNewApi/FundMNFInfo?pageIndex=1&pageSize=200&plat=Android&appType=ttjj&product=EFund&Version=1&deviceid=123&Fcodes=110011");
            spider.addUrl("http://open.huobigroup.com/market/tickers");
            spider.addPipeline(pipeline);
            spider.start();
            spider.stop();
        }
    }
}
