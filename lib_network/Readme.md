网络请求组件lib_network使用简介


    -lib_network相当于一个网络请求adapter
    -通过调用网络请求组件完成请求
    -无需关心openApi的accessToken
    -无需关心其他网络请求配置参数

调用方式

-引入lib_network 

    compile "com.kk.app:lib_network:0.0.1"

-执行初始化方法：

    public class Application extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            //网络请求初始化
            NetworkConfig.setup(this, "prd");
            Fresco.initialize(this);
        }
    }

调用接口简介

请求openApi接口

    public static <T> void queryOpenApi(String serviceName, JSONObject data, NetworkCallback<T> callback)
    
    //关联生命周期网络请求
    public static <T> String queryOpenApi(Activity activity, String serviceName, JSONObject data, NetworkCallback<T> callback)
    public static <T> String queryOpenApi(Fragment fragment, String serviceName, JSONObject data, NetworkCallback<T> callback)
    
请求接口(动态密钥／报文加密)

    public static <T> void query(String url, JSONObject data, NetworkCallback<T> callback)
    public static <T> void query(String url, JSONObject data, String method, NetworkCallback<T> callback)
    public static <T> String query(String url, String data, String method, NetworkCallback<T> callback)
    
    //关联生命周期网络请求
    public static <T> String query(Activity activity, String url, JSONObject data, NetworkCallback<T> callback)
    public static <T> String query(Fragment fragment, String url, JSONObject data, NetworkCallback<T> callback)
    public static <T> String query(Activity activity, String url, JSONObject data, String method, NetworkCallback<T> callback)
    public static <T> String query(Fragment fragment, String url, JSONObject data, String method, NetworkCallback<T> callback)
    public static <T> String query(Activity activity, String url, String data, String method, NetworkCallback<T> callback)
    public static <T> String query(Fragment fragment, String url, String data, String method, NetworkCallback<T> callback)
    
    //同步请求中间件
    public static String query(String url, String data, String method)
    
请求中台接口

    public static String queryServiceJson(String url, String data)
    public static String queryServiceForm(String url, String data)
    
请求其他API接口(无加密)

    public static <T> void queryApi(String url, JSONObject data, NetworkCallback<T> callback)
    public static <T> void queryApi(String url, Object data, String method, NetworkCallback<T> callback)