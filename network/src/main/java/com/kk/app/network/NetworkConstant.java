package com.kk.app.network;

/**
 * @author kk
 * @datetime: 2019/08/08
 * @desc:
 */
public interface NetworkConstant {
    /**
     * Component
     */
    String KRY_MINE_COMPONENT = "network";

    /**
     * get  请求方式
     */
    String KRY_ACTION_GET = "GET";

    /**
     * post 请求方式
     */
    String KRY_ACTION_POST = "POST";

    /**
     * 设置网络请求 setting
     */
    String KRY_ACTION_SETTING = "SETTING";


    /**
     * 参数 url
     */
    String KEY_URL = "url";

    /**
     * 参数 headers
     */
    String KEY_HEADER = "headers";

    /**
     * 参数 data
     */
    String KEY_DATA = "data";

    /**
     * 以下key为内部使用的key
     */
    String PRIVATE_KEY_CONTENT_TYPE = "Content-type";


    /**
     * 失败重试次数
     */
    String KEY_RETRY = "retry";

    /**
     * 最大重试次数
     */
    int MAX_RETRY_COUNT = 5;

    /**
     * 已重试的次数计数
     */
    String PRIVATE_KEY_RETRY_COUNT = "retry_count";


    /**
     * 连接超时
     */
    String KEY_CONNECT_TIMEOUT = "connectTimeout";

    /**
     * 写入超时
     */
    String KEY_WRITE_TIMEOUT = "writeTimeout";

    /**
     * 读取超时
     */
    String KEY_READ_TIMEOUT = "readTimeout";


    /**
     * result
     */
    String KEY_RESULT = "result";

    /**
     * response headers
     */
    String KEY_RESPONSE_HEADERS = "responseHeaders";

    /**
     * http Code
     */
    String KEY_HTTP_CODE = "httpCode";
}
