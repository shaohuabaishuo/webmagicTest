package guji;

import guwen.ShenYinYuPageProcessor;
import guwen.ShenYinYuPipeline;
import us.codecraft.webmagic.Spider;

public class ChuCiStart {

    public static void main(String[] args) {
//        getBook("论语", "学而");
//        getBook("荀子", "哀公");
//        getBook("淮南子", "原道训");
        getBook("孟子", "告子");
//        getBook("庄子", "齐物");
//        getBook("庄子", "大宗师");
//        getBook("孟子", "滕文");
    }

    private static void getBook(String bookName, String chapter) {
        ChuCiPageProcessor pageProcessor = new ChuCiPageProcessor(bookName, chapter);

        Spider spider = Spider.create(pageProcessor);

        ChuCiPipeline pipeline = new ChuCiPipeline();

//        spider.addUrl("https://www.zggdwx.com/search?q=" + bookName);
        spider.addUrl("https://hz.api.w3cbus.com/search/zggdwx?q=" + bookName + "&page=1&per_page=16&region=cnHangzhou");
        spider.addPipeline(pipeline);
        spider.thread(1);
        spider.start();
        spider.stop();
    }
}
