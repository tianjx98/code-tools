package cn.tianjx98.views.about;

import com.github.appreciated.card.ClickableCard;
import com.github.appreciated.card.content.IconItem;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import cn.tianjx98.views.MainLayout;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    public AboutView() {
        // setSpacing(false);
        //
        // Image img = new Image("images/empty-plant.png", "placeholder plant");
        // img.setWidth("200px");
        // add(img);
        //
        // add(new H2("This place intentionally left empty"));
        // add(new Paragraph("It’s a place where you can grow your own UI 🤗"));
        //
        // setSizeFull();
        // setJustifyContentMode(JustifyContentMode.CENTER);
        // setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        // getStyle().set("text-align", "center");


        clickableCardExample("images/empty-plant.png", "123","1221312");
    }

    public void clickableCardExample(String imagePath, String title, String description) {
        Image img = new Image(imagePath, title);
        img.setWidth("50px");
        img.setHeight("50px");
        ClickableCard card = new ClickableCard(componentEvent -> Notification.show("A ClickableCard was clicked!"),
                        new IconItem(img, title, description));
        card.setWidth("100%");
        add(card);
    }

}
