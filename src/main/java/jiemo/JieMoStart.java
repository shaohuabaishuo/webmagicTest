package jiemo;

import huobi.BuyDetail;
import huobi.HuobiPageProcessor;
import huobi.HuobiPipeline;
import us.codecraft.webmagic.Spider;

import java.math.BigDecimal;
import java.util.Arrays;

public class JieMoStart {
    public static void main(String[] args) {
        Integer pageNum = 1;
        do {
            JieMoPageProcessor pageProcessor = new JieMoPageProcessor();
            JieMoPipeline pipeline = new JieMoPipeline();
            Spider spider = Spider.create(pageProcessor);
//        spider.addUrl("https://fundmobapi.eastmoney.com/FundMNewApi/FundMNFInfo?pageIndex=1&pageSize=200&plat=Android&appType=ttjj&product=EFund&Version=1&deviceid=123&Fcodes=110011");
            spider.addUrl("https://api281.jiemo100.com/topic/index/index?login_token=darling_679081d8_0_4dadd172dfc07a1905d0539555dfe253&group_id=39456&page=" + pageNum + "&page_size=20");
            spider.addPipeline(pipeline);
            pageNum++;
            spider.start();
            spider.stop();
        }
        while (pageNum > 1000);

    }
}
