package cn.tianjx98.views.about;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import cn.tianjx98.views.MainLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends IFrame {

    public AboutView() {
        super("home/index.html");
        setHeightFull();
        setWidthFull();

        //setSpacing(false);
        //
        //Image img = new Image("images/empty-plant.png", "placeholder plant");
        //img.setWidth("200px");
        //add(img);
        //
        //add(new H2("This place intentionally left empty"));
        //add(new Paragraph("It’s a place where you can grow your own UI 🤗"));
        //
        //setSizeFull();
        //setJustifyContentMode(JustifyContentMode.CENTER);
        //setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        //getStyle().set("text-align", "center");
    }

}
