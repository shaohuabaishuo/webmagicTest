package guji;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

public class ChuCiPageProcessor implements PageProcessor {

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    private String bookName;

    private String chapter;
    private String nextUrl;

    public ChuCiPageProcessor(String bookName) {
        this.bookName = bookName;
    }

    public ChuCiPageProcessor(String bookName, String chapter) {
        this.bookName = bookName;
        this.chapter = chapter;
    }

    @Override
    public void process(Page page) {
        if (page.getUrl().get().equals("https://hz.api.w3cbus.com/search/zggdwx?q=" + bookName + "&page=1&per_page=16&region=cnHangzhou")) {
            page.putField("bookName", page.getHtml().xpath("//*[@id=\"page-search\"]/div/div[1]/div[1]/a/em"));
            SearchResult searchResult = JSONObject.parseObject(page.getRawText(), SearchResult.class);
            List<SearchEntity> searchEntities = searchResult.getData().getItems();
            if (CollectionUtils.isNotEmpty(searchEntities)) {
                SearchEntity searchEntity = searchEntities.stream().filter(item -> item.getTitle().equals(bookName)).findFirst().orElse(null);
                if (searchEntity != null) {
                    page.addTargetRequest(searchEntity.getUrl());
                    nextUrl = searchEntity.getUrl();
                }
            }

        } else if (page.getUrl().get().equals(nextUrl)) {
            List<String> urlList = page.getHtml().xpath("//*[@id=\"directory\"]/div/a/@href").all();
            List<String> nextUrlList = new ArrayList<>();
            urlList.forEach(item -> {
                String url = "https://www.zggdwx.com/" + item;
                nextUrlList.add(url);
            });
            page.addTargetRequests(nextUrlList);
        } else {
            String title = page.getHtml().xpath("//*[@id=\"page-book-content\"]/div/div[1]/h1/text()").toString();
            List<String> content = page.getHtml().xpath("//*[@id=\"page-book-content\"]/div/div/p/text()").all();
            page.putField("title", title);
            page.putField("searchTitle",chapter);
            page.putField("bookName", bookName);
            page.putField("content", content);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
