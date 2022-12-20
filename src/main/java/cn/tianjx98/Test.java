package cn.tianjx98;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import com.alibaba.fastjson.JSON;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

public class Test {
    public static void main(String[] args) {
        final HashMap<String, Object> param = new HashMap<>();
        param.put("prompt", "cron表达式每三分钟执行一次怎么写");
        param.put("max_tokens", 1000);
        param.put("model", "text-davinci-003");
        final HttpResponse response = HttpUtil.createPost("https://api.openai.com/v1/completions")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer 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")
                        .body(JSON.toJSONString(param)).execute();
        System.out.println(response.body());
    }

    private RequestEntity createRequestEntity(HttpServletRequest request, String url)
                    throws URISyntaxException, IOException {
        String method = request.getMethod();
        HttpMethod httpMethod = HttpMethod.resolve(method);
        MultiValueMap<String, String> headers = parseRequestHeader(request);
        byte[] body = parseRequestBody(request);
        return new RequestEntity<>(body, headers, httpMethod, new URI(url));
    }


    private byte[] parseRequestBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }

    private MultiValueMap<String, String> parseRequestHeader(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        List<String> headerNames = Collections.list(request.getHeaderNames());
        for (String headerName : headerNames) {
            List<String> headerValues = Collections.list(request.getHeaders(headerName));
            for (String headerValue : headerValues) {
                headers.add(headerName, headerValue);
            }
        }
        return headers;
    }
}
