# Android Support Design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

# Jackson
-dontwarn com.fasterxml.jackson.databind.**
-keep class org.codehaus.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class com.fasterxml.jackson.annotation.** { *; }
-keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
    public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *;
}

# Jurnal API
-keep public class id.jurnal.mobile.internal.data.remote.request.** {
  public void set*(***);
  public *** get*();
}
-keep public class id.jurnal.mobile.internal.data.remote.response.** {
  public void set*(***);
  public *** get*();
}

# Retrofit
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8

# OkHTTP
-dontwarn okio.**
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Parceler
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }
-keep class org.parceler.Parceler$$Parcels

# JodaTime
-keep class org.joda.** { *; }

# Others
-adaptresourcefilenames **.xsd,**.wsdl,**.xml,**.js,**.json,**.properties,**.gif,**.jpg,**.png
-allowaccessmodification
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes Exceptions
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-useuniqueclassmembernames

# Unit Test
-ignorewarnings
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontnote org.apache.http.**
-dontnote android.net.http.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**
-dontwarn org.hamcrest.**
-dontwarn com.squareup.javawriter.JavaWriter
-dontwarn org.mockito.**