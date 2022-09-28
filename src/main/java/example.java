import java.util.Arrays;
import java.util.List;

public class example {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("139", "138", "137", "136", "135", "134", "159", "158", "157", "150", "151", "152", "188", "187", "182", "183", "184", "178", "130", "131", "132", "156", "155", "186", "185", "176", "133", "153", "189", "180", "181", "177");
        int listLength = list.size();
        int i = Integer.parseInt(String.valueOf(Math.round(Math.random() * listLength)));
        String num = list.get(i);
        for (int j = 0; j < 8; j++) {
            num = num + Math.round(Math.random() * 10);
        }
        System.out.println(num);
    }
}
