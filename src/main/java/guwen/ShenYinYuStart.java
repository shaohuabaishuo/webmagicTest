package guwen;

import us.codecraft.webmagic.Spider;

public class ShenYinYuStart {
    public static void main(String[] args) {
        getBook("呻吟语");
//        getBook("大学章句集注");
//        getBook("中庸");
    }


    private static void getBook(String bookName){
        ShenYinYuPageProcessor pageProcessor = new ShenYinYuPageProcessor(bookName);

        Spider spider = Spider.create(pageProcessor);

        ShenYinYuPipeline pipeline = new ShenYinYuPipeline();

        spider.addUrl("http://shiwens.com/search.html?k="+bookName);
        spider.addPipeline(pipeline);
        spider.thread(5);
        spider.start();
        spider.stop();
    }
}
