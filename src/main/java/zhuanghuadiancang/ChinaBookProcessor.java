package zhuanghuadiancang;

import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChinaBookProcessor implements PageProcessor {

    private String bookName;
    private String chapter;

    public ChinaBookProcessor(String bookName, String chapter) {
        this.bookName = bookName;
        this.chapter = chapter;
    }

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private SearchTypeEnum searchTypeEnum = SearchTypeEnum.START_SEARCH;

    @Override
    public void process(Page page) {
        page.putField("searchType", searchTypeEnum);
        switch (searchTypeEnum) {
            case START_SEARCH:
                searchBook(page);
                break;
            case BOOK_NAME:
                searchChapter(page);
                break;
            case CHAPTER_NAME:
                List<String> content = page.getHtml()
                        .xpath("//*[@id=\"content\"]/p/text()")
                        .all()
                        .stream()
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.toList());
                page.putField("content", content);
                page.putField("bookName", bookName);
                page.putField("chapter", chapter);
                break;
        }

    }

    private void searchChapter(Page page) {
        List<String> chapterList = page.getHtml().xpath("//*[@id=\"booklist\"]/li/a").all();
        for (String chapterLabel : chapterList) {
            Html html = new Html(chapterLabel);
            String chapterAddress = html.xpath("/html/body/a/@href").get();
            String chapter = html.xpath("/html/body/a/@title").get();
            System.out.println("章节名:"+chapter);
            if (chapter.equals(this.chapter)) {
                page.addTargetRequest(chapterAddress);
                page.putField("chapter", chapter);
            }
        }
        page.putField("bookName", bookName);
        searchTypeEnum = SearchTypeEnum.CHAPTER_NAME;
    }

    private void searchBook(Page page) {
        List<String> searchList = page.getHtml().xpath("/html/body/div[2]/div[2]/div/div/table/tbody/tr/td[1]/a").all();
        for (String aLabel : searchList) {
            Html html = new Html(aLabel);
            String bookAddress = html.xpath("/html/body/a/@href").get();
            String bookName = html.xpath("/html/body/a/@title").get();
            if (bookName.equals(this.bookName)) {
                page.addTargetRequest(bookAddress);
            }
        }
        if (page.getTargetRequests().isEmpty()) {
            String bookAddress = page.getHtml().xpath("/html/body/div[2]/div[2]/div/div/table/tbody/tr/td[1]/a/@href").all().get(0);
            page.addTargetRequest(bookAddress);
        }
        page.putField("bookName", bookName);
        page.putField("chapter", chapter);
        searchTypeEnum = SearchTypeEnum.BOOK_NAME;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
