package cn.tianjx98.components;

import com.vaadin.flow.component.html.Div;

/**
 * @author 18872653103
 * @date 2022/8/9 17:43
 */
public class TestBox extends Div {
    public TestBox() {
        setHeight("50px");
        setWidth("50px");
        setText("test");
        getStyle().set("background-color", "gray");
        getStyle().set("boarder", "1px");
    }
}
