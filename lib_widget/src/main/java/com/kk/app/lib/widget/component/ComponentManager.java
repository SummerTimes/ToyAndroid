package com.kk.app.lib.widget.component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kk
 * @datetime: 2020/01/16
 * @desc: 通过
 */
public class ComponentManager {

    private static List<IComponentInit> APPLICATION = new ArrayList<>();

    static void register(IComponentInit component) {
        if (component != null) {
            APPLICATION.add(component);
        }
    }

    public static List<IComponentInit> getApplication() {
        return APPLICATION;
    }

}
