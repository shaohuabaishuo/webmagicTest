import java.util.Arrays;
import java.util.List;

public class BeanUtil {

    public static void main(String[] args) {
        System.out.println(getMobile());

        String[] FirstThreeNumber = {"134","135","136","137","138","139","147","150","152","157","158","159","172","178","182","183","184","187","188","198","130","131","132","145","155","156","166","171","175","176","185","186","166","133","149","153","173","177","180","181","189","199"};

//随机获取前三位手机号的index

        int n= (int)(Math.random() * FirstThreeNumber.length);

//获取手机前三位

        String yy = FirstThreeNumber[n];

//循环获取手机号后8位

        for(int i = 0; i < 8; i++){

            int x = (int)(Math.random() * 9);

            yy = yy + x;

        };
        System.out.println(yy);
    }

    public static String getMobile() {
        List<String> list = Arrays.asList("139", "138", "137", "136", "135", "134", "159", "158", "157", "150", "151", "152", "188", "187", "182", "183", "184", "178", "130", "131", "132", "156", "155", "186", "185", "176", "133", "153", "189", "180", "181", "177");
        int listLength = list.size();
        int i = Integer.parseInt(String.valueOf(Math.round(Math.random() * listLength)));
        String num = list.get(i);
        for (int j = 0; j < 8; j++) {
            num = num + Math.round(Math.random() * 10);
        }
        return num;
    }

}
