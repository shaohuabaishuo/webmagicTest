package guwen;

import us.codecraft.webmagic.Spider;

import java.util.*;
import java.util.stream.Collectors;

public class ShenYinYuStart {

    public static void main(String[] args) {
//        getBook("呻吟语","");
        getBook("大学","");
//        getBook("大学章句集注");
//        getBook("中庸");
//        getBook("传习录","");
//        getBook("商君书","王霸");
//        getBook("尚书", "虞书·尧典");
//        getBook("金刚经","");
//        getBook("老子","");
//        getBook("楞严经","");
//        getBook("庄子","");
//        getBook("楚辞","");
//        getBook("韩非","");
    }


    private static void getBook(String bookName, String chapter) {
        ShenYinYuPageProcessor pageProcessor = new ShenYinYuPageProcessor(bookName, chapter);

        Spider spider = Spider.create(pageProcessor);

        ShenYinYuPipeline pipeline = new ShenYinYuPipeline();

        spider.addUrl("http://shiwens.com/search.html?k=" + bookName);
        spider.addPipeline(pipeline);
        spider.thread(5);
        spider.start();
        spider.stop();
    }
}
