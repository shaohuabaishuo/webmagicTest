package deepseek;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.internal.filter.ValueNodes;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class deepSeek {
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    //sk-7dee0b6e34044f7da0d3df13712d0122
    private static final String API_KEY = "sk-7dee0b6e34044f7da0d3df13712d0122";

    public static String sendRequest(String prompt) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求头
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setDoOutput(true);

            // 构建请求体
            String jsonInputString = String.format("{\"model\": \"deepseek-chat\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}", prompt);
           //将jsonInputString转换为实体类


            // 发送请求
            connection.getOutputStream().write(jsonInputString.getBytes());

            // 读取响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String response = sendRequest("白首相知犹按剑全文");

        try {
            // 解析JSON响应
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);

            // 获取content字段
            String content = rootNode
                    .path("choices")
                    .path(0)
                    .path("message")
                    .path("content")
                    .asText();

            // 按照换行打印原文
            System.out.println(content.replace("\\n", "\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
