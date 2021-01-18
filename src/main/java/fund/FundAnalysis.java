package fund;

import us.codecraft.webmagic.Spider;

public class FundAnalysis {

    public static void main(String[] args) {
        FundAnalysisPageProcessor pageProcessor=new FundAnalysisPageProcessor();
        FundAnalysisPipeline pipeline=new FundAnalysisPipeline();
        Spider spider = Spider.create(pageProcessor);
        spider.addUrl("https://fundmobapi.eastmoney.com/FundMNewApi/FundMNFInfo?pageIndex=1&pageSize=200&plat=Android&appType=ttjj&product=EFund&Version=1&deviceid=123&Fcodes=110011");
        spider.addPipeline(pipeline);
        spider.start();
        spider.stop();
    }
}
