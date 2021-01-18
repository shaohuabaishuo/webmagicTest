package guwen;

import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShenYinYuPageProcessor implements PageProcessor {

    private String bookName;

    private String chapter;

    public ShenYinYuPageProcessor(String bookName) {
        this.bookName = bookName;
    }

    public ShenYinYuPageProcessor(String bookName, String chapter) {
        this.bookName = bookName;
        this.chapter = chapter;
    }

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        System.out.println(page.getUrl());
        if (page.getUrl().get().equals("http://shiwens.com/search.html?k=" + bookName)) {
            page.putField("bookName", page.getHtml().xpath("//*[@id=\"txtKey\"]/@value"));
            page.putField("nextUtl", page.getHtml().xpath("//div[@class='cont']/h1/a[@target='_blank']/@href"));
            page.putField("flag", 0);
            String requestUrl = "http://shiwens.com/" + page.getHtml().xpath("//div[@class='cont']/h1/a[@target='_blank']/@href");
            page.addTargetRequest(requestUrl);
        } else if (page.getUrl().get().contains("book_")) {
            List<String> nextUrl = new ArrayList<>();
            List<String> requests = page.getHtml().xpath("//div[@class='bookcont']//span//a/@href").all();
            requests.forEach(item -> {
                String url = "http://shiwens.com/" + item;
                nextUrl.add(url);
            });
            page.addTargetRequests(nextUrl);
            page.putField("flag", 1);
        } else if (page.getUrl().get().contains("bookv_")) {
            String title = page.getHtml().xpath("//h1//span/b/text()").toString();
            String author = page.getHtml().xpath("//p[@class='source']//text()").toString();
            List<String> content = page.getHtml().xpath("//div[@class='contson']//p/text()").all();
            page.putField("title", StringUtils.isBlank(title) ? "" : title);
            page.putField("author", StringUtils.isBlank(author) ? "" : author);
            page.putField("content", content.isEmpty() ? Collections.emptyList() : content);
            page.putField("chapter",chapter);
            page.putField("flag", 2);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
