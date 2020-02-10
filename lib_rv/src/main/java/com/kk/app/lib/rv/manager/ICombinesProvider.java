package com.kk.app.lib.rv.manager;

import java.util.List;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public interface ICombinesProvider {

    List<IFloorCombine> createCombines(int key);

}
