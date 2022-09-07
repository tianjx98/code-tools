package cn.tianjx98.views.tools.text;

import java.util.HashMap;

import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.util.StringUtils;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import cn.tianjx98.aop.annotations.Tab;
import cn.tianjx98.views.MainLayout;
import lombok.extern.log4j.Log4j2;

@PageTitle("Mybatis SQL参数替换")
@Route(value = "mybatis-sql-parser", layout = MainLayout.class)
// @Menu(value = "Sql Tools", iconClass = "la la-book")
@Tab(value = "SQL参数替换", group = "textTool", order = -1)
@Log4j2
public class MybatisSqlLogReplaceView extends VerticalLayout {
    TextArea sqlInput;
    TextArea output;

    public MybatisSqlLogReplaceView() {
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
        sqlInput = new TextArea("SQL日志");
        sqlInput.setHeightFull();
        sqlInput.setWidthFull();
        col1.add(sqlInput);

        sqlInput.setValueChangeMode(ValueChangeMode.EAGER);
        sqlInput.addValueChangeListener(event -> {
            try {
                output.setValue(getResult());
            } catch (Exception e) {
                Notification.show("解析异常: " + e.getMessage());
                log.error(e);
            }
        });

        final VerticalLayout col3 = new VerticalLayout();
        col3.setHeightFull();
        col3.setWidthFull();
        page.add(col3);
        output = new TextArea("拼接SQL语句");
        output.setWidthFull();
        output.setHeightFull();
        col3.add(output);
    }

    private String getResult() {
        if (!StringUtils.hasLength(sqlInput.getValue())) {
            return "";
        }

        final HashMap<String, String> map = resolveSQL();

        if (!StringUtils.hasLength(map.get("param"))) {
            return map.get("sql");
        }
        final String[] originParams = map.get("param").split(", ");
        final Object[] formatParams = new String[originParams.length];
        for (int i = 0; i < originParams.length; i++) {
            formatParams[i] = formatParam(originParams[i].trim());
        }
        final String formatSql = map.get("sql").replace("%", "$$$").replace("?", "%s");
        return String.format(formatSql, formatParams).replace("$$$", "%");
    }

    private static final String SQL_FLAG = "Preparing: ";
    private static final String PARAM_FLAG = "Parameters: ";

    private HashMap<String, String> resolveSQL() {
        String lines[] = sqlInput.getValue().split("\\r?\\n");
        final HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < lines.length; i++) {
            final String line = lines[i];
            final int sqlIndex = line.indexOf(SQL_FLAG);
            if (sqlIndex >= 0) {
                map.put("sql", line.substring(sqlIndex + SQL_FLAG.length()));
                final String nextLine = lines[i + 1];
                map.put("param", nextLine.substring(nextLine.indexOf(PARAM_FLAG) + PARAM_FLAG.length()));
            }
        }
        return map;
    }

    private String formatParam(String originParam) {
        if ("null".equalsIgnoreCase(originParam)) {
            return originParam;
        }
        final int firstCommon = originParam.indexOf("(");
        final String value = originParam.substring(0, firstCommon);
        final String type = originParam.substring(firstCommon + 1, originParam.length() - 1);
        return needSurroundedWithApostrophe(type) ? "'" + value + "'" : value;

    }

    private boolean needSurroundedWithApostrophe(String type) {
        switch (type) {
            case "Long":
                return false;
            default:
                return true;
        }
    }

}
