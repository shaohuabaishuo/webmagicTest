package guwen;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class ShenYinYuPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        Integer flag = resultItems.get("flag");

        switch (flag){
            case 0:
                String bookName =resultItems.get("bookName").toString();
                System.out.println("书名："+bookName);
                break;
            case 2:
                String title = resultItems.get("title").toString();
                String author = resultItems.get("author").toString();
                List<String> content = resultItems.get("content");
                System.out.println("标题：" + title);
                System.out.println("作者：" + author);
                content.forEach(System.out::println);
                break;
            default:
                break;
        }


    }
}
