package guwen;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Comparator;
import java.util.List;

public class ShenYinYuPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        Integer flag = resultItems.get("flag");

        switch (flag) {
            case 0:
                String bookName = resultItems.get("bookName").toString();
                System.out.println("书名：" + bookName);
                break;
            case 2:
                String title = resultItems.get("title").toString();
//                System.out.println(title);
                String author = resultItems.get("author").toString();
                if (title.contains(resultItems.get("chapter").toString())) {
                    List<String> content = resultItems.get("content");
                    System.out.println("标题：" + title);
                    System.out.println("作者：" + author);
                    content.forEach(item -> {
                        String[] split = item.split("。");
                        for (int i = 0; i < split.length; i++) {
                            System.out.println(split[i]);
                        }
                    });
                ShenYinYuStart.list.put(title,content);
                    content.forEach(System.out::println);
                }
                break;
            default:
                break;
        }


    }
}
