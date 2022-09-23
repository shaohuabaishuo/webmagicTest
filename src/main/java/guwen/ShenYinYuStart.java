package guwen;

import us.codecraft.webmagic.Spider;

import java.util.*;
import java.util.stream.Collectors;

public class ShenYinYuStart {

    public static Map<String, List<String>> list = new HashMap<>();

    public static void main(String[] args) {
//        getBook("呻吟语","谈道");
//        getBook("坛经","");
//        getBook("大学","");
//        getBook("大学章句集注");
//        getBook("中庸","");
//        getBook("孟子","告子");
//        getBook("传习录","黄省");
//        getBook("商君书","王霸");
//        getBook("尚书", "说命");
//        getBook("金刚经","");
//        getBook("老子", "");
//        getBook("楞严经","");
//        getBook("庄子","人间");
//        getBook("楚辞","九");
//        getBook("韩非","");
//        getBook("论语","里仁");
//        getBook("易传","");
//        getBook("周易","");
//        getBook("荀子","宥坐");
//        getBook("荀子","子道");
//        getBook("荀子","性恶");
//        getBook("礼记","");
//        getBook("资治通鉴","唐纪");
        getBook("史记","仲尼");
//        getBook("国语","");
//        getBook("四书集注","");
//        getBook("韩非子","解老");
        /*try {
            Thread.sleep(40000);
            List<String> collect = list.keySet().stream().sorted(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String o1Str = o1.substring(o1.indexOf("第"), o1.indexOf("章"));
                    String o2Str = o2.substring(o2.indexOf("第"), o2.indexOf("章"));
                    long l = chineseNumber2Int(o1Str);
                    long x = chineseNumber2Int(o2Str);
                    return Integer.parseInt(String.valueOf(l - x));
                }
            }).collect(Collectors.toList());
            for (String s : collect) {
                List<String> content = list.get(s);
                System.out.println("标题:"+s);
                content.forEach(item -> {
                    String[] split = item.split("。");
                    for (int i = 0; i < split.length; i++) {
                        System.out.println(split[i]);
                    }
                });
            }
//            System.out.println("嘎嘎：" + collect);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

    public static long chineseNumber2Int(String chineseNumber) {
        String aval = "零一二三四五六七八九";
        String bval = "十百千万亿";
        int[] bnum = {10, 100, 1000, 10000, 100000000};
        long num = 0;
        char[] arr = chineseNumber.toCharArray();
        int len = arr.length;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < len; i++) {
            char s = arr[i];
            //跳过零
            if (s == '零') continue;
            //用下标找到对应数字
            int index = bval.indexOf(s);
            //如果不在bval中，即当前字符为数字，直接入栈
            if (index == -1) {
                stack.push(aval.indexOf(s));
            } else { //当前字符为单位。
                int tempsum = 0;
                int val = bnum[index];
                //如果栈为空则直接入栈
                if (stack.isEmpty()) {
                    stack.push(val);
                    continue;
                }
                //如果栈中有比val小的元素则出栈，累加，乘N，再入栈
                while (!stack.isEmpty() && stack.peek() < val) {
                    tempsum += stack.pop();
                }
                //判断是否经过乘法处理
                if (tempsum == 0) {
                    stack.push(val);
                } else {
                    stack.push(tempsum * val);
                }
            }
        }
        //计算最终的和
        while (!stack.isEmpty()) {
            num += stack.pop();
        }
        return num;
    }


    private static void getBook(String bookName, String chapter) {
        ShenYinYuPageProcessor pageProcessor = new ShenYinYuPageProcessor(bookName, chapter);

        Spider spider = Spider.create(pageProcessor);

        ShenYinYuPipeline pipeline = new ShenYinYuPipeline();

        spider.addUrl("http://shiwens.com/search.html?k=" + bookName);
        spider.addPipeline(pipeline);
        spider.thread(5);
        spider.start();
        spider.stop();
    }
}
