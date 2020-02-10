#网络请求的拦截器

##1. OpenApi请求拦截器: NetworkOpenApiInterceptor

功能：对发起的openApi请求进行url加密，解析返回的结果
使用方式：

        //配置请求头信息
        JSONObject header = new JSONObject();
        try{
            header.put("Connection", "Keep-Alive");
            header.put("chnflg", "Android");
            header.put("version", "2.6.0");//2.5.0以下的密钥不一样
        } catch(Exception e) {
            e.printStackTrace();
        }
        //配置openApi的参数
        JSONObject config = new JSONObject();
        try{
            config.put("sn", "977800923");
            config.put("grant_type", "client_credentials");
            config.put("channelId", "1");
            config.put("secret", "YX5S75jD1U1A30IDdnC4Sukj7X9342Y59");
            config.put("appid", "xs679pUa8S");
            config.put("service_name", "bl.app.member.login");
            config.put("salt", "0");
            config.put("access_token", accessToken);
            config.put("tokenKey", tokenKey);
            config.put("timestamp", String.valueOf( System.currentTimeMillis()));
            config.put("url", OPEN_API_SERVER_URL);
        } catch(Exception e) {
            e.printStackTrace();
        }

        JSONObject data = new JSONObject();
        try{
            //{"loginId":"13032137672","password":"1cb992b934f3ee668723e403c0e93012",
            // "channelId":1,"appName":"i百联","timestamp":"20170809135511",
            // "sysid":"1103","snNo":"977800923","loginIp":"192.168.1.183",
            // "appVersion":"2.6.0","channellSN":"","mobileType":"android"}
            data.put("loginId", user);
            data.put("password", MD5Utils.string2MD5(password, 32));
            data.put("channelId", 1);
            data.put("appName", "i百联");
            data.put("secret", "YX5S75jD1U1A30IDdnC4Sukj7X9342Y59");
            data.put("sysid", "1103");
            data.put("appid", "xs679pUa8S");
            data.put("snNo", "977800923");
            data.put("loginIp", "192.168.1.183");
            data.put("appVersion", "2.6.0");
            data.put("channellSN", "");
            data.put("mobileType", "android");

            data.put("service_name", "bl.app.member.login");
            data.put("timestamp", FORMATTER.format(System.currentTimeMillis()));

        } catch(Exception e) {
            e.printStackTrace();
        }
        //示例： 登录请求
        CC.obtainBuilder("network")
                .setActionName("post")
                .addParam("url", "/app/login/userLogin.htm") //只需指定接口名
                .addParam("data", data) //请求参数列表
                .addParam("headers", header) //指定头信息（不需指定Content-type）
                .addParam("config", config) //指定openApi参数
                .addInterceptor(NetworkOpenApiInterceptor.get()) //指定用openApi的拦截器
                .build()
                .callAsyncCallbackOnMainThread(loginCallback);

##2. 加解密拦截器： NetworkEncryptInterceptor

功能：
    通过中间件解析网络请求的接口，对请求参数进行加密，对返回结果进行解密
    解析返回结果：返回resCode为00100000时被看做请求成功，否则为失败

使用方法：
        //示例：请求资源位接口
        JSONObject data = new JSONObject();
        try{
            JSONObject resReq = new JSONObject();
            resReq.put("resourceId","7150,7151,7152,7153,7154,7155");
            data.put("otherresource",resReq);
            data.put("activity", new JSONArray());
        } catch(Exception e) {
            e.printStackTrace();
        }

        //配置请求头信息
        JSONObject header = new JSONObject();
        try{
            header.put("Connection", "Keep-Alive");
            header.put("chnflg", "Android");
            header.put("version", "2.6.0");//2.5.0以下的密钥不一样
        } catch(Exception e) {
            e.printStackTrace();
        }
        CC.obtainBuilder("network")
                .setActionName("post")
                .addParam("url", "https://mobile.bl.com/app/site/queryAdDeploy.htm") //请求地址
                .addParam("data", data)
                .addParam("headers", header)
                .addInterceptor(NetworkEncryptInterceptor.get())//加解密拦截器
                .build()
                .callAsyncCallbackOnMainThread(new IComponentCallback() {
                    @Override
                    public void onResult(CC cc, CCResult result) {
                        textView.setText(JsonFormat.format(result.toString()));
                    }
                });
