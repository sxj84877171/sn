# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\XXW\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keepattributes *Annotation*
 -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
      **[] $VALUES;
      public *;
    }

    -keep class org.greenrobot.greendao.**{*;}
    -keep public interface org.greenrobot.greendao.**
    -keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
    }
    -keep class **$Properties
    -keep class net.sqlcipher.database.**{*;}
    -keep public interface net.sqlcipher.database.**
    -dontwarn net.sqlcipher.database.**
    -dontwarn org.greenrobot.greendao.**

    -optimizationpasses 5
    -dontusemixedcaseclassnames
    -dontskipnonpubliclibraryclasses
    -dontpreverify
    -verbose
    -dontwarn
    -dontskipnonpubliclibraryclassmembers
    -ignorewarnings
    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


     -keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

    # 保持 native 方法不被混淆
    -keepclasseswithmembernames class * {
        native <methods>;
    }
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet, int);
    }


    # 泛型与反射
    -keepattributes Signature
    -keepattributes EnclosingMethod
    -keepattributes *Annotation*
