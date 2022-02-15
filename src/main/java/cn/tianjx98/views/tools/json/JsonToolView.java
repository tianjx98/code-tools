package cn.tianjx98.views.tools.json;

import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import cn.tianjx98.views.MainLayout;

@PageTitle("JSON字段提取")
@Route(value = "jsonExtractor", layout = MainLayout.class)
public class JsonToolView extends VerticalLayout {
    TextArea jsonInput;
    TextArea output;

    TextField attrPath;
    TextField format;
    Button extractButton;

    public JsonToolView() {
        setHeightFull();
        setWidthFull();
        final HorizontalLayout page = new HorizontalLayout();
        page.setHeightFull();
        page.setWidthFull();
        page.setAlignItems(Alignment.CENTER);
        add(page);
        final VerticalLayout col1 = new VerticalLayout();
        col1.setHeightFull();
        col1.setWidthFull();
        page.add(col1);
        jsonInput = new TextArea();
        jsonInput.setHeightFull();
        jsonInput.setWidthFull();
        col1.add(jsonInput);

        final VerticalLayout col2 = new VerticalLayout();
        col2.setHeightFull();
        col2.setWidthFull();
        col2.setAlignItems(Alignment.CENTER);
        page.add(col2);
        attrPath = new TextField("字段路径");
        col2.add(attrPath);
        format = new TextField("拼接格式(占位符为%s)");
        col2.add(format);
        extractButton = new Button("提取");
        extractButton.addClickListener(event -> {
            output.setValue(getResult());
        });
        col2.add(extractButton);



        final VerticalLayout col3 = new VerticalLayout();
        col3.setHeightFull();
        col3.setWidthFull();
        page.add(col3);
        output = new TextArea();
        output.setWidthFull();
        output.setHeightFull();
        col3.add(output);

        // final FluentGridLayout gridLayout = new FluentGridLayout();
        // gridLayout.withTemplateRows(new Flex(1)).withTemplateColumns(new Flex(1), new Flex(1), new
        // Flex(1)).withRowAndColumn(jsonInput, 1,1).withRowAndColumn(attrPath,2,1)
        // .withRowAndColumn(extractButton, 2,2).withRowAndColumn(output,3,1);
        // add(gridLayout);
    }

    private String getResult() {
        final String[] parts = attrPath.getValue().split("\\.");
        final JSONArray jsonArray = getJsonArray(parts);
        final String formatStr = this.format.getValue();
        return jsonArray.stream().map(o -> ((JSONObject) o).getString(parts[parts.length - 1]))
                        .map(val -> String.format(formatStr, val)).collect(Collectors.joining(","));
    }

    private JSONArray getJsonArray(String[] parts) {
        if (parts.length == 1) {
            return JSON.parseArray(jsonInput.getValue());

        } else if (parts.length == 2) {
            return JSON.parseObject(jsonInput.getValue()).getJSONArray(parts[0]);
        } else {
            JSONObject jsonObject = JSON.parseObject(jsonInput.getValue());
            for (int i = 0; i < parts.length - 2; i++) {
                jsonObject = jsonObject.getJSONObject(parts[i]);
            }
            return jsonObject.getJSONArray(parts[parts.length - 2]);
        }
    }

}
