package cn.tianjx98.views.quickstart;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import cn.tianjx98.aop.annotations.Menu;
import cn.tianjx98.views.MainLayout;

@PageTitle("Quick Start")
@Route(value = "quick-start", layout = MainLayout.class)
// @RouteAlias(value = "", layout = MainLayout.class)
//@Menu(value = "Quick Start", iconClass = "la la-file", order = 0)
public class QuickStartView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public QuickStartView() {
        setMargin(true);
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
