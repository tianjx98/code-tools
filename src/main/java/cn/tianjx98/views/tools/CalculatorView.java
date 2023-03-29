package cn.tianjx98.views.tools;

import cn.hutool.Hutool;
import cn.hutool.core.util.StrUtil;
import cn.tianjx98.aop.annotations.Tab;
import cn.tianjx98.views.MainLayout;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Options;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@PageTitle("计算器")
@Route(value = "calculator", layout = MainLayout.class)
// @Menu(value = "Sql Tools", iconClass = "la la-book")
@Tab(value = "计算器", group = "textTool", order = 2)
@Log4j2
public class CalculatorView extends VerticalLayout {
    TextArea sqlInput;
    TextArea output;

    static {
        AviatorEvaluator.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.EVAL);
    }

    public CalculatorView() {
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
        col1.add(new Span("参考文档: https://www.yuque.com/boyan-avfmj/aviatorscript/lvabnw"));
        sqlInput = new TextArea("表达式");
        sqlInput.setHeightFull();
        sqlInput.setWidthFull();
        col1.add(sqlInput);

        sqlInput.setValueChangeMode(ValueChangeMode.EAGER);
        sqlInput.addValueChangeListener(event -> {
            String result = getResult();
            output.setValue(StringUtils.hasText(result) ? result : "");
        });

        final VerticalLayout col3 = new VerticalLayout();
        col3.setHeightFull();
        col3.setWidthFull();
        page.add(col3);
        output = new TextArea("结果");
        output.setWidthFull();
        output.setHeightFull();
        col3.add(output);
    }

    private String getResult() {
        if (!StringUtils.hasLength(sqlInput.getValue())) {
            return "";
        }
        Map<String, Object> param = new HashMap<>();
        String lines[] = sqlInput.getValue().split("\\r?\\n");
        return Arrays.stream(lines).map(line -> {
            try {
                if (extractParam(param, line)) {
                    return "变量已存储";
                }
                return execute(param, line);
            } catch (Exception e) {
                return "error";
            }
        }).collect(Collectors.joining("\n"));
    }

    private static String execute(Map<String, Object> param, String expr) {
        return AviatorEvaluator.execute(expr.replace("/", "*1.0/"), param).toString();
    }

    private static boolean extractParam(Map<String, Object> param, String line) {
        if (line.contains("=") && !line.contains("==")) {
            for (String part : line.split(";")) {
                if (StringUtils.hasText(part)) {
                    String[] split = part.split("=");
                    param.put(split[0], new BigDecimal(execute(param, split[1])));
                }
            }
            return true;
        }
        return false;
    }
}
