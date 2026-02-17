#AppsFlayer
-keep class com.appsflyer.** { *; }
-keep public class com.android.installreferrer.** { *; }

#WebChromeClient
-keep class * extends android.webkit.WebChromeClient { *; }

# Prevent obfuscation of Box2D native-bound classes
-keep class com.badlogic.gdx.physics.box2d.** { *; }

# Keep native method references
-keepclasseswithmembers class * {
    native <methods>;
}

# Keep all gdx core and backend classes
-keep class com.badlogic.gdx.** { *; }