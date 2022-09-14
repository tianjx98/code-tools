package cn.tianjx98.views.tools;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import cn.tianjx98.aop.annotations.Menu;
import cn.tianjx98.components.tabs.Tabs;
import cn.tianjx98.infra.constants.Constant;
import cn.tianjx98.views.MainLayout;

@PageTitle("文本处理工具")
@Route(value = "text-tools", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Menu(value = "文本处理工具", iconClass = "la la-file")
public class TextToolView extends VerticalLayout {
    TextArea jsonInput;
    TextArea output;

    TextField attrPath;
    TextField format;
    TextField delimiter;
    Button extractButton;

    public TextToolView() {
        setHeightFull();
        setWidthFull();
        // final HorizontalLayout page = new HorizontalLayout();
        // page.setHeightFull();
        // page.setWidthFull();
        // page.setAlignItems(Alignment.CENTER);
        // add(page);
        // final VerticalLayout col1 = new VerticalLayout();
        // col1.setHeightFull();
        // col1.setWidthFull();
        // page.add(col1);
        // jsonInput = new TextArea();
        // jsonInput.setHeightFull();
        // jsonInput.setWidthFull();
        // col1.add(jsonInput);
        final Tabs tabs = new Tabs("textTool", Constant.Packages.VIEW_PACKAGE);
        add(tabs, tabs.getContent());
        final Footer footer =
                        new Footer(new Anchor("https://beian.miit.gov.cn/", "鄂ICP备2021019583号-1", AnchorTarget.BLANK));
        footer.getStyle().set("font-size", "5px");
        footer.setWidthFull();
        footer.getStyle().set("text-align", "center");
        add(footer);
    }

}
