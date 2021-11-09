package cn.tianjx98.views.quickstart;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import cn.tianjx98.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Quick Start")
@Route(value = "quick-start", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
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
