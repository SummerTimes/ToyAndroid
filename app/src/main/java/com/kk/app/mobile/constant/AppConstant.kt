package com.kk.app.mobile.constant

/**
 * @author kk
 * @datetime: 2019-07-12
 * @desc:组件/常量
 */
interface AppConstant {
    companion object {
        /**
         * 组件名
         */
        const val KRY_APP_COMPONENT = "member"
        /**
         * 组件Action
         */
        const val KRY_GOTO_MAIN = "gotoMainActivity"
        /**
         * 打开指定的 Action Fragment
         */
        const val KRY_GOTO_ACTION_FRAGMENT = "gotoAction"
        /**
         * getVersionName
         */
        const val KRY_VERSION_NAME = "getVersionName"
        /**
         * 获取渠道号
         */
        const val KRY_CHANNEL_NO = "getChannelNo"
        /**
         * 获取渠道号
         */
        const val KRY_DEVICE_ID = "getDeviceId"

        /**
         * 缓存名
         */
        const val APP_NET_WORK = "app_net_work"

        /**
         * 网络标签
         */
        const val NET_TYPE = "net_type"
    }
}