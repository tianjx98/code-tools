package cn.tianjx98.components.tabs;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import org.reflections.Reflections;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import cn.tianjx98.aop.annotations.Tab;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * @author 18872653103
 * @date 2022/8/18 15:16
 */
@Log4j2
public class Tabs extends com.vaadin.flow.component.tabs.Tabs {


    @Getter
    final VerticalLayout content = new VerticalLayout();

    public Tabs(String group, String packages) {
        super();
        add(loadTabs(group, packages));
        content.setHeightFull();
        content.setWidthFull();
        ((cn.tianjx98.components.tabs.Tab) getSelectedTab()).active(content);
        addSelectedChangeListener(event -> ((cn.tianjx98.components.tabs.Tab) event.getSelectedTab()).active(content));

    }

    private Component[] loadTabs(String group, String packages) {
        Reflections reflections = new Reflections(packages);
        Set<Class<?>> tabs = reflections.getTypesAnnotatedWith(Tab.class);
        final Object[] objects = tabs.stream().map(tab -> {
            //
            final Tab tag = tab.getAnnotation(Tab.class);
            if (!group.equals(tag.group())) {
                return null;
            }
            try {
                return new cn.tianjx98.components.tabs.Tab(tag.value(),
                                (Component) tab.getDeclaredConstructor().newInstance(), tag.order());
            } catch (Exception e) {
                log.error(e);
                return null;
            }
        }).filter(Objects::nonNull).sorted().toArray();
        return Arrays.copyOf(objects, objects.length, Component[].class);
    }
}
