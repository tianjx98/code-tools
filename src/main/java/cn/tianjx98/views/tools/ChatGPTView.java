package cn.tianjx98.views.tools;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyPressEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.tianjx98.Application;
import cn.tianjx98.aop.annotations.Tab;
import cn.tianjx98.views.MainLayout;
import lombok.extern.log4j.Log4j2;

@PageTitle("ChatGPT")
@Route(value = "chat-gpt", layout = MainLayout.class)
@Tab(value = "ChatGPT", group = "textTool", order = 5)
@Log4j2
@Component
public class ChatGPTView extends VerticalLayout {
    private String apiKey;
    TextField question;
    TextArea answer;


    public ChatGPTView() {
        setHeightFull();
        setWidthFull();
        final VerticalLayout col1 = new VerticalLayout();
        add(col1);
        question = new TextField("问题");
        question.setHeight("80px");
        question.setWidth("700PX");
        answer = new TextArea("答案");
        answer.setHeight("550px");
        answer.setWidth("700PX");
        col1.add(question);
        col1.add(answer);

        question.addKeyPressListener(Key.ENTER, new ComponentEventListener<KeyPressEvent>() {
            @Override
            public void onComponentEvent(KeyPressEvent event) {
                if (StringUtils.hasText(question.getValue())) {
                    System.out.println(question.getValue());
                    final HashMap<String, Object> param = new HashMap<>();
                    param.put("prompt", question.getValue());
                    param.put("max_tokens", 1000);
                    param.put("model", "text-davinci-003");
                    answer.setValue(getResult(param));
                }
            }
        });
    }

    private String getResult(Map<String, Object> param) {
        try {
            final HttpRequest request = HttpUtil.createPost("https://api.openai.com/v1/completions")
                            .header("Content-Type", "application/json").header("Authorization", "Bearer " + getApiKey())
                            .body(JSON.toJSONString(param));
            System.out.println(request);
            final String body = request.execute().body();

            System.out.println(body);
            return ((JSONObject) JSON.parseObject(body).getJSONArray("choices").get(0)).getString("text").trim();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private String getApiKey() {
        if (apiKey == null) {
            return apiKey = Application.context.getEnvironment().getProperty("chatgpt.api-key");
        }
        return apiKey;
    }
}
