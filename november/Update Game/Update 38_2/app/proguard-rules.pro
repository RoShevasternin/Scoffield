#AppsFlayer
-keep class com.appsflyer.** { *; }
-keep public class com.android.installreferrer.** { *; }

#WebChromeClient
-keep class * extends android.webkit.WebChromeClient { *; }