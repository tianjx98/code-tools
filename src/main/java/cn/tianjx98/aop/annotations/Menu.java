package cn.tianjx98.aop.annotations;

import java.lang.annotation.*;

/**
 * @author 18872653103
 * @date 2022/8/9 16:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE})
public @interface Menu {
    /**
     * @return 菜单标题
     */
    String value();

    /**
     * @return 菜单图标
     */
    String iconClass() default "la la-file";

    /**
     * @return 优先级, 越小越靠前
     */
    int order() default 1;

}
