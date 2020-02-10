package com.kk.app.mobile.web.register

import com.kk.app.mobile.web.NavigationFunction
import com.kk.app.web.function.register.IFunction
import com.kk.app.web.function.register.IFunctionRegisterManager

/**
 * @author kk
 * @datetime 2019-08-06
 * @desc
 */
class FunctionRegisterManager : IFunctionRegisterManager {
    override fun onBind(functions: MutableList<IFunction>) {
        addFunction(functions, NavigationFunction())
    }

    private fun addFunction(functions: MutableList<IFunction>, iFunction: IFunction) {
        functions.add(iFunction)
    }
}