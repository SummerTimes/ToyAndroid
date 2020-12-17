#WebLib简介：

###功能：

通过注入一个本地的JS文件作为JAVA和远端Js进行交互：

###原理：

通过改变window的IFrame的Src从而使得Android web内核调用WebViewClient的
shouldOverrideUrlLoading(WebView view, String url)方法，在这里面进行信息的拦截和处理

 JS
 
     //产生IFrame并更改IFrame的Src
    function LoadNative(url) {

            if(!messagingIframe){
                messagingIframe = doc.createElement('iframe');
                messagingIframe.style.display = 'none';
                window.document.documentElement.appendChild(messagingIframe);
            }


            messagingIframe.src = url;
            console.log("调用"+document.getElementsByTagName('iframe').length);
    }
    
JAVA:

    boolean shouldOverrideUrlLoading(WebView view, String url){
    
            //当拦截到我们定义的     ctjsbridge://就说明我们的数据来了需要处理了
            
    
    }
    


###并发的实现：

由于性能问题，并不是我们每次改变Src都会立马回调shouldOverrideUrlLoading方法，而当前一个没有响应的时候
远端又使得src发生变化，这就使得前一个src改变事件被吃掉，不再响应了；

解决方案：

创建一个缓存list，来确保每个都执行完毕后再走下一个。如果这时候有新加入的则加入到list中,


     window.CTMethodList=new Array();
     
     function LoadMethod(targetName, actionName, data, callback){
                     var dataString = encodeURIComponent(JSON.stringify(data));
                      var timestamp = Date.parse(new Date());
                     var identifier = (targetName + actionName + dataString+timestamp).hashCode().toString();
     
                     window.CTCallBackList[identifier] = callback;
                     var methodName=targetName+"#"+actionName;
                     var url = "ctjsbridge://component?callbackIdentifier=" + identifier + "&data=" + dataString + "&targetName=" + targetName + "&actionName=" + actionName+"&methodName="+methodName;
     
     
                     if(window.CTMethodList.length==0){
                          setTimeout(function(){
                                console.log("初始方法");
                                var startUrl="ctjsbridge://component?callbackIdentifier="+"&startMethod";//当methodList为空时加入startMethod作为有方法需要调用的通知
                                LoadNative(startUrl);
                             },30);
                      }
     
                      window.CTMethodList.push(url);//推入数据
      }

Java收到startMethod后就调用JSBridge的flateMethod方法取CTMethodList的第一个数据同时把该数据移除；
这样周而复始就会把所有的都取完从而把所有数据都处理完毕；


###回调的实现：

因为Java和Js之间是无法拿到互相之间的对象的；因而只能通过字符串形式进行通信；
所以每个都需要生成一个唯一标示来进行身份的判别；

    window.CTCallBackList = {};


    var dataString = encodeURIComponent(JSON.stringify(data));
    var timestamp = Date.parse(new Date());
    var identifier = (apiName + dataString + timestamp).hashCode().toString();
    

//identifier 就是根据时间戳生成的唯一标志，src改变时这个唯一标志也作为数据传给Java

java需要回调Js时就把这个标识重新传递过来；

    final String javascriptCommand = String.format(JS_HANDLE_MESSAGE_FROM_JAVA, indentifer, data.status, jsonData);
    loadUrl(javascriptCommand);

这样就完成整个流程


###Java层面的函数注册和分发：

每个函数都有一个类似键值的东西具体到项目里就是Target+Action;
都是预埋好的。

    webView.registerFunction(WebConstant.METHOD.SHOW_ALERT_DIALOG, new INativeCallBack() {

            private BLDialog mDialog;
            private BLDialog.Builder mBuilder;

            @Override
            public void onCall(String method, String data, final String url, final IJSCallFunction ijsCallFunction) {
                try {

                    JSDialogEntity jsDialogEntity = new Gson().fromJson(data, JSDialogEntity.class);

                    showDialog(jsDialogEntity, ijsCallFunction, url);


                } catch (Exception e) {
                    e.printStackTrace();


                }
     }


registerFunction做的操作就是把这个INativeCallBack放到一个ArrayMap当中；当上面实际JS调用的时候，会对键值进行匹配看哪个Fuction会进行
处理；





