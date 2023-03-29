package cn.tianjx98.views.tools;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import cn.tianjx98.aop.annotations.Tab;
import cn.tianjx98.views.MainLayout;

@PageTitle("字符串运算")
@Route(value = "distinctTool", layout = MainLayout.class)
@Tab(value = "字符串运算", group = "textTool", order = 3)
public class DistinctToolView extends VerticalLayout {
    TextArea input;
    TextArea input2;

    Select<String> select;

    TextArea output;

    Button extractButton;

    public DistinctToolView() {
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
        input = new TextArea("源数据");
        input.setHeightFull();
        input.setWidthFull();
        col1.add(input);

        final VerticalLayout col2 = new VerticalLayout();
        col2.setHeightFull();
        col2.setWidthFull();
        col2.setAlignItems(Alignment.CENTER);
        page.add(col2);
        select = new Select<>("减去", "交集", "去重");
        select.setValue("减去");
        // attrPath = new TextField("字段路径");
        col2.add(select);
        extractButton = new Button("执行");
        extractButton.addClickListener(event -> {
            output.setValue(getResult());
        });
        col2.add(extractButton);

        input2 = new TextArea("源数据2");
        input2.setHeightFull();
        input2.setWidthFull();
        col2.add(input2);


        final VerticalLayout col3 = new VerticalLayout();
        col3.setHeightFull();
        col3.setWidthFull();
        page.add(col3);
        output = new TextArea("结果");
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
        if (!StringUtils.hasLength(input.getValue())) {
            return "";
        }
        String type = select.getValue();
        if ("减去".equals(type)) {
            Set<String> ipt2 = toStream(input2.getValue()).collect(Collectors.toSet());
            return toStream(input.getValue()).filter(s -> !ipt2.contains(s)).collect(Collectors.joining("\n"));
        } else if ("交集".equals(type)) {
            Set<String> ipt2 = toStream(input2.getValue()).collect(Collectors.toSet());
            return toStream(input.getValue()).filter(ipt2::contains).collect(Collectors.joining("\n"));
        } else if ("去重".equals(type)) {
            return toStream(input.getValue()).distinct().collect(Collectors.joining("\n"));
        }
        return "";
    }

    private Stream<String> toStream(String value) {
        return Arrays.stream(value.split("\\r?\\n"));
    }


}
