package cn.tianjx98.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.extra.mail.MailUtil;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping()
public class NotifyController {
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/s-QNHmEETMSMD196DhsCDA")
    public ResponseEntity<String> sendEmail(HttpServletRequest request) throws URISyntaxException, IOException {
        MailUtil.sendText("973970940@qq.com", String.format("访问提醒(%s)%s", request.getHeader("X-Real-IP"), DateUtil.now()), "");
        return restTemplate.exchange("http://localhost:3000/s-QNHmEETMSMD196DhsCDA", HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }

    //@PostMapping("/socket.io")
    //public ResponseEntity<String> updateSendEmail(HttpServletRequest request) throws URISyntaxException, IOException {
    //    MailUtil.sendText("973970940@qq.com", String.format("访问提醒(%s)%s", request.getHeader("X-Real-IP"), DateUtil.now()), "");
    //    System.out.println(request.getRequestURI());
    //    return restTemplate.exchange("http://localhost:3000" + request.getRequestURI(), HttpMethod.POST, HttpEntity.EMPTY, String.class);
    //}

    private ResponseEntity<String> route(RequestEntity requestEntity) {
        return restTemplate.exchange(requestEntity, String.class);
    }

    private RequestEntity createRequestEntity(HttpServletRequest request, String url) throws URISyntaxException, IOException {
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
