package zhuanghuadiancang;

import com.alibaba.fastjson.JSON;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChinaBookStart {

    public static Map<String, List<String>> list = new HashMap<>();

    public static void main(String[] args) {
        getBook("孟子", "告子");
    }


    private static void getBook(String bookName, String chapter) {
        ChinaBookProcessor pageProcessor = new ChinaBookProcessor(bookName, chapter);

        Spider spider = Spider.create(pageProcessor);
        Request request = new Request("https://www.zhonghuadiancang.com/e/search/index.php");
        Map<String, Object> map = new HashMap<>();
        map.put("tbname", "bookname");
        map.put("show", "title,writer");
        map.put("tempid", 1);
        map.put("keyboard", bookName);
        request.setRequestBody(HttpRequestBody.form(map, "utf-8"));
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setMethod(HttpConstant.Method.POST);
        request.setDownloader(new HttpClientDownloader());
        spider.addRequest(request);

        ChinaBookPipeline pipeline = new ChinaBookPipeline();

//        spider.addUrl("https://www.zhonghuadiancang.com/e/search/index.php");
        spider.addPipeline(pipeline);
        spider.thread(5);
        spider.start();
        spider.stop();
    }
}
