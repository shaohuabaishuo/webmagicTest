package jiemo;

import com.alibaba.fastjson.JSONArray;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JieMoPipeline implements Pipeline {
    HashSet<Topics> topics = new HashSet<>();

    @Override
    public void process(ResultItems resultItems, Task task) {
        Set<Topics> list= resultItems.get("topics");
        list.forEach(item->{
            System.out.println(LocalDateTime.ofEpochSecond(item.getCreate_time(),0, ZoneOffset.ofHours(8))+"--"+item.getContent());
        });
    }

}
