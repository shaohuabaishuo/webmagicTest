package fund;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FundAnalysisPageProcessor implements PageProcessor {
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    private static final Map<String, Integer> map = new HashMap<>();

    static {
        map.put("110011", 123);
    }


    @Override
    public void process(Page page) {
        JSONObject jsonObject = JSONObject.parseObject(page.getRawText());
        System.out.println(jsonObject);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
