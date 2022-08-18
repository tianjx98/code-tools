package cn.tianjx98.components.tabs;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

import java.io.PipedReader;

/**
 * @author 18872653103
 * @date 2022/8/18 15:36
 */
@Getter
public class Tab extends com.vaadin.flow.component.tabs.Tab implements Comparable<Tab> {

    private final int order;
    private final Component content;


    public Tab(String label, Component content, int order) {
        super(label);
        this.order = order;
        this.content = content;
    }

    @Override
    public int compareTo(Tab o) {
        return order - o.getOrder();
    }

    public void active(HasComponents container) {
        container.removeAll();
        container.add(content);
    }
}
