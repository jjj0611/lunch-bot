package org.woowacourse.lunchbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public class UserService {
    //    private static final String TOKEN = "Bearer " +
    private static final String TOKEN = "Bearer " + "xoxb-946531805872-953180476354-gyl145eaYz6cJQkOLQovT1we";
    private static final String defaultName = "이름이 뭐예요?";
    private final WebClient webClient;
    private final String BASE_URL = "https://slack.com/api";
    private Map<String, String> user; // key: userId, value:

    public UserService() {
        webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.AUTHORIZATION, TOKEN)
                .build();
    }

    public void getDisplayName() {
        String response = webClient.get()
                .uri("/users.profile.get",
                        uriBuilder -> uriBuilder.queryParam("user", "UUG4C346S")
                                .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String name = parseDisplayName(response);
        System.out.println("name : "+name);
    }

    private String parseDisplayName(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return jsonNode.get("profile").get("display_name").toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void getAllUserId() {
        String response = webClient.get()
                .uri("/users.list")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("###########response##########" + response.toString());
        getAllUserId(response);
    }

    public void getAllUserId(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

//        JsonNode memebers = jsonNode.get("members");
//        try {
//            abc obj = objectMapper.readValue("{\"id\":\"iddd\"}
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        int membersLength = memebers.size();
//        for (int i = 0; i < membersLength; i++) {
//            user.put(memebers.get(i).get("id").toString(), defaultName);
////        }
//        JsonNode jsonNode2 = memebers.get(0);
//        System.out.println("@@@@@ jsonNode1 : " + memebers);
//        System.out.println("@@@@@ jsonNode2 : " + jsonNode2);
//        System.out.println("@@@@@ jsonNode3 : " + jsonNode2.get("id"));
//        System.out.println("@@@@@ jsonNode3 : " + JSON.toString(jsonNode2.get("id")));
    }
}

