package jiemo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class JieMoPageProcessor implements PageProcessor {
    /**
     * 购买的价钱，购买的
     */
    Set<Topics> topicList=new HashSet<>();

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        JSONObject jsonObject = JSONObject.parseObject(page.getRawText());
        JSONObject data = (JSONObject)jsonObject.get("data");
        List<Topics> topics = JSONArray.parseArray(data.getString("topics"), Topics.class);
        topics.forEach(item->{
            topicList.add(item);
            page.putField("topics",topicList);
        });
    }

    @Override
    public Site getSite() {
        return site;
    }
}
