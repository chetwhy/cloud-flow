package cn.yccoding.gzh.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @Author YC
 * @create 2020/3/6
 */
@Component
public class RestHttpClient {

    @Autowired
    private RestTemplate restTemplate;

    public String doPost(String toUrl, String requestJson) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);
        return restTemplate.postForEntity(toUrl, httpEntity, String.class).getBody();
    }

    public String doGet(String toUrl) {
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate.getForEntity(toUrl, String.class).getBody();
    }

    public <T> T doGet(String toUrl,Class<T> responseType,Object... uriVariables) {
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        Object body = restTemplate.getForEntity(toUrl, responseType,uriVariables).getBody();
        return (T) body;
    }

    public static void main(String[] args) {

    }
}
