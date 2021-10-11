package guji;

import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class ChuCiPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        String title = resultItems.get("title").toString();
        String bookName = resultItems.get("bookName").toString();
        List<String> content = resultItems.get("content");
        if (CollectionUtils.isEmpty(content)) {
            System.out.println("未找到内容!");
        }

        if(title.contains(resultItems.get("searchTitle"))){
            System.out.println("书名:"+bookName);
            System.out.println("标题:"+title);
            content.forEach(System.out::println);
        }

    }
}
