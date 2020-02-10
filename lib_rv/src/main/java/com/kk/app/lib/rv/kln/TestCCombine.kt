import com.kk.app.lib.rv.manager.AbstractFloorCombine
import com.kk.app.lib.rv.manager.IUI
import com.kk.app.lib.rv.kln.TestFloor

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
class TestCCombine(key: Int) : AbstractFloorCombine(key) {

    override fun onUIReady(ui: IUI?, alreadyInsert: Boolean) {
        for (i: Int in 1..1000) {
            val kotlinFloor = TestFloor()
            kotlinFloor.data = "哈哈哈$i"
            add(kotlinFloor)
        }
        infoDataSetChanged()
    }
}