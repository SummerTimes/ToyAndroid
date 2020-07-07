# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#
##================混淆操作=======================
##指定代码的压缩级别
#-optimizationpasses 5
##包明不混合大小写
#-dontusemixedcaseclassnames
##不去忽略非公共的库类
#-dontskipnonpubliclibraryclasses
# #优化  不优化输入的类文件
#-dontoptimize
# #预校验
#-dontpreverify
# #混淆时是否记录日志
#-verbose
# # 混淆时所采用的算法
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
##优化时允许访问并修改有修饰符的类和类的成员
#-allowaccessmodification
##将文件来源重命名为“SourceFile”字符串
#-renamesourcefileattribute SourceFile
######################记录生成的日志数据,gradle build时在本项目根目录输出################
##apk 包内所有 class 的内部结构
#-dump class_files.txt
##列出从 apk 中删除的代码
#-printusage unused.txt
#
###================================Base 基线包混淆前后的映射============================
#-printmapping mapping.txt
#
##保留行号
#-keepattributes SourceFile,LineNumberTable
#
##保护注解
#-keepattributes *Annotation*
#-keepattributes Signature
#-keepattributes EnclosingMethod
#-keep public class com.google.vending.licensing.ILicensingService
#-keep public class com.android.vending.licensing.ILicensingService
#-keep public class * extends android.app.Fragment
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#
##如果引用了v4或者v7包
#-dontwarn android.support.**
#-keep interface android.support.v4.app.** { *; }
#
##保持Activity中View及其子类入参的方法
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
##不混淆资源类
#-keep class **.R$* {
#    public static <fields>;
#}
#
#############混淆保护自己项目的部分代码以及引用的第三方jar包library-end##################
#-keep public class * extends android.view.View {
#    public <init>(...);
#    public void set*(...);
#    *** get*();
#}
#
##保持 native 方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
##保持 Parcelable 不被混淆
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#
##保持 Serializable 不被混淆
#-keepnames class * implements java.io.Serializable
##保持 Serializable 不被混淆并且enum 类也不被混淆
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    !static !transient <fields>;
#    !private <fields>;
#    !private <methods>;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
##保持 @JavascriptInterface 不被混淆
#-keepclassmembers class ** {
#    @android.webkit.JavascriptInterface <methods>;
#}
#
###---------------Begin: proguard configuration for Gson ----------
#-keep public class com.google.gson.**
#-keep public class com.google.gson.** {public private protected *;}
#-keep public class com.project.mocha_patient.login.SignResponseData { private *; }
#
#-dontwarn okio.**
#-dontwarn com.squareup.okhttp.**
#-dontwarn okhttp3.**
#-dontwarn javax.annotation.**
#-dontwarn com.android.volley.toolbox.**
#-dontwarn com.facebook.infer.**
#-keep class com.facebook.**{*;}
#
#############################Json#################################
#-dontwarn com.google.**
#-keep class com.google.gson.** {*;}
#-keep class com.google.protobuf.** {*;}
#
#############################Glide#################################
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep class * extends com.bumptech.glide.module.AppGlideModule {
# <init>(...);
#}
#-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
#  *** rewind();
#}
#
#############################Retrofit#################################
#-dontwarn okio.**
#-dontwarn javax.annotation.**
#
#############################rxjava#################################
#-dontwarn rx.**
#-keep class rx.**{*;}
#
###keep all innerclass fields
#-keepclassmembers class **.*$*{
#    <fields>;
#}
#-keepclassmembers class cn.com.bailian.bailianmobile.page.Main.entity.**{
#    <fields>;
#}
#-keepclassmembers class * extends cn.com.bailian.data.entity.BLEntity{
#     <fields>;
# }
