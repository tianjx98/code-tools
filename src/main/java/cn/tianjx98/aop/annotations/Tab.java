package cn.tianjx98.aop.annotations;

import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 18872653103
 * @date 2022/8/9 16:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE})
public @interface Tab {
    /**
     * @return tab页标题
     */
    String value();

    /**
     * @return tab页分组值, 指定分组值的tab才会生成一个tab标签
     */
    String group();

    /**
     * @return 优先级, 越小越靠前
     */
    int order() default 1;

}
