package zhuanghuadiancang;

import com.alibaba.fastjson.JSON;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Html;

import java.awt.*;
import java.util.List;

public class ChinaBookPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        if (SearchTypeEnum.CHAPTER_NAME.equals(resultItems.get("searchType"))) {
            List<String> contentList = resultItems.get("content");
            Object bookName = resultItems.get("bookName");
            Object chapter = resultItems.get("chapter");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "\033[1;32m" + bookName + "\t\t" + chapter + "\033[1;0m");
            for (String str : contentList) {
                String[] split = str.split("。");
                for (String s : split) {
                    System.out.println(s);
                }

            }
        }
    }
}
