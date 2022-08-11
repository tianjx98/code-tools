package cn.tianjx98.views.atest;

import cn.tianjx98.aop.annotations.Menu;
import cn.tianjx98.components.Row;
import cn.tianjx98.components.TestBox;
import cn.tianjx98.views.MainLayout;
import com.github.appreciated.card.ClickableCard;
import com.github.appreciated.card.content.IconItem;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Test")
@Route(value = "test", layout = MainLayout.class)
@Menu(value = "测试", order = -1)
public class TestView extends VerticalLayout {

    public TestView() {
        add("123");
        final Row row = new Row();
        row.add(new TestBox());
        row.add(new TestBox());
        row.add(new TestBox());
        row.add(new TestBox());
        row.add(new TestBox());
        add(row);
    }

}
