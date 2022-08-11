package cn.tianjx98.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.dom.Style;

public class Row extends Div implements FlexComponent {

    public Row() {
        Style style = getStyle();
        style.set("display", "flex");
        style.set("flex-direction", "row");
        style.set("flex-wrap", "wrap");
    }
}
