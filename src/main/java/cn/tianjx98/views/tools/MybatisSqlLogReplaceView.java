package cn.tianjx98.views.tools;

import java.util.HashMap;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import cn.tianjx98.aop.annotations.Menu;
import cn.tianjx98.views.MainLayout;

@PageTitle("Mybatis SQL参数替换")
@Route(value = "mybatis-sql-parser", layout = MainLayout.class)
@Menu(value = "Sql Tools", iconClass = "la la-book")
public class MybatisSqlLogReplaceView extends VerticalLayout {
    TextArea sqlInput;
    TextArea paramInput;
    TextArea output;

    TextField attrPath;
    TextField format;
    TextField delimiter;
    Button generateButton;

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
        sqlInput.setHeight("400px");
        sqlInput.setWidthFull();
        col1.add(sqlInput);
        // paramInput = new TextArea("参数");
        // paramInput.setHeight("350px");
        // paramInput.setWidthFull();
        // col1.add(paramInput);


        final VerticalLayout col2 = new VerticalLayout();
        col2.setHeightFull();
        col2.setWidthFull();
        col2.setAlignItems(Alignment.CENTER);
        page.add(col2);
        generateButton = new Button("生成SQL");
        generateButton.addClickListener(event -> {
            output.setValue(getResult());
        });
        col2.add(generateButton);



        final VerticalLayout col3 = new VerticalLayout();
        col3.setHeightFull();
        col3.setWidthFull();
        page.add(col3);
        output = new TextArea();
        output.setWidthFull();
        output.setHeightFull();
        col3.add(output);
    }

    private String getResult() {

        final HashMap<String, String> map = resolveSQL();

        final String[] originParams = map.get("param").split(",");
        final Object[] formatParams = new String[originParams.length];
        for (int i = 0; i < originParams.length; i++) {
            formatParams[i] = formatParam(originParams[i].trim());
        }
        final String formatSql = map.get("sql").replace("%", "$$$").replace("?", "%s");
        return String.format(formatSql, formatParams).replace("$$$", "%");
    }

    private static final String SQL_FLAG = "==>  Preparing: ";
    private static final String PARAM_FLAG = "==> Parameters: ";

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
