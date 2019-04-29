LabCave Mediation Android SDK
=====
The current version has been tested using API 21, 23, 25 and 26 and require a minSdkVersion >= 17

## Download SDK

Download our sdk, unzip the file and add all files in lib folder to your project's lib folder. Then add these lines to your build.gradle:

### Importing to your project
**Add to build.gradle:**

```java
repositories {
  flatDir {
    dirs 'libs'
  }
} 
```

```java
compile(name: 'labcavemediation-base-2.6.0', ext: 'aar')
compile 'com.google.android.gms:play-services-base:+'
```

**Recommended:**

```java
dependencies {
    compile 'com.android.support:multidex:1.0.2'
}
```

**AdColony**

```java
dependencies {
    compile(name: 'labcavemediation-mediation-adcolony-noso-2.6.0', ext: 'aar')
    compile 'com.google.android.gms:play-services-ads:+'
}

sourceSets {
    main {
      jniLibs.srcDirs = ['libs']
    }
}
```
**Admob**

```java
dependencies {
    compile(name: 'labcavemediation-mediation-admob-2.6.0', ext: 'aar')
    compile 'com.google.android.gms:play-services-ads:+'
    compile 'com.google.android.ads.consent:consent-library:1.0.6'

}
```

**AppLovin**

```java
dependencies {
    compile(name: 'labcavemediation-mediation-applovin-fat-2.6.0', ext: 'aar')
    compile 'com.google.android.gms:play-services-ads:+'
}
```

**Chartboost**

```java
dependencies {
    compile(name: 'labcavemediation-mediation-chartboost-fat-2.6.0', ext: 'aar')
    compile 'com.google.android.gms:play-services-ads:+'
}
```

**Facebook**

```java
dependencies {
    compile(name: 'labcavemediation-mediation-facebook-2.6.0', ext: 'aar')
    compile(name: 'AudienceNetwork', ext: 'aar')
    compile 'com.google.android.gms:play-services-ads:+'
}
```

**InMobi**

```java
dependencies {
    compile(name: 'labcavemediation-mediation-inmobi-fat-2.6.0', ext: 'aar')
    compile "com.android.support:design:+"
}
```

**StartApp**

```java
dependencies {
     compile(name: 'labcavemediation-mediation-startapp-fat-2.6.0', ext: 'aar')
}
```

**UnityAds**

```java
dependencies {
    compile(name: 'labcavemediation-mediation-unityads-fat-2.6.0', ext: 'aar')
}
```
**Vungle**

```java
dependencies {
    compile(name: 'labcavemediation-mediation-vungle-fat-2.6.0', ext: 'aar')
    compile 'com.google.android.gms:play-services- ads:+'
}
```


## Integrate SDK

Once you have added all files and gradle lines it's time to initialize the sdk. Important you must initialize the sdk and the begining of the execution of your app and only once.


```java
LabCaveMediation.INSTANCE.init(this, APP_HASH);
```
The appHash is the hash of your app, you can get it in https://mediation.labcavegames.com/panel/apps, "context" is your activity context.

When you init the sdk then you can show ads. Important the mediation sdk auto fetch all ads for you, when you call the init method also will fecth the first ads, so you only need to call the showMethods. Display ads with the corresponding action according to the type desired:

```java
LabCaveMediation.INSTANCE.showBanner(context, tag);
LabCaveMediation.INSTANCE.showBanner(labcaveBannerView, tag);

LabCaveMediation.INSTANCE.showInterstitial(context, tag);
LabCaveMediation.INSTANCE.showRewardedVideo(context, tag);
```
You must pass the conext of your activity and a string where the ad will be shown "main-menu", "options"... also can be an empty string.

If Banner ads are used, it is recommended to pause/resume ads with the onPause/onResume
method:

```java
@Override protected void onPause() {
    super.onPause();
    LabCaveMediation.INSTANCE.pause();
}

@Override protected void onResume() {
    super.onResume();
    LabCaveMediation.INSTANCE.resume();
}
```
### Advance integration

The sdk offers a delegate where you can receive the events of the ads. Important the the method "addListener"
add a new listener, so check you don't add the same listener more than once. The sdk will call all the listener added. You can remove a listener or all listeners added.

```java

 LabCaveMediation.INSTANCE.removeListener(@NonNull LabCaveMediationListener listener);

 LabCaveMediation.INSTANCE.clearListener();

 LabCaveMediation.INSTANCE.addListener(new LabCaveMediationListener() {
      // When the sdk is already initialized, if everything is ok, state will be true.
      @Override public void onInit(boolean state) {

      }
      // Will be called when any ad is loaded, it will tell you the type MediationType.BANNER, MediationType.INSTERSTITIAL and MediationType.REWARDED_VIDEO
      @Override public void onMediationTypeLoad(MediationType type) {

      }
      // When an ad is closed
      @Override public void onClose(MediationType type, String name, String extra) {

      }
      // When an ad is clicked
      @Override public void onClick(MediationType type, String name, String extra) {

      }
      // When we received an error loading or showing an ad
      @Override public void onError(String description, MediationType type, String extra) {

      }
      // When an ad is showed
      @Override public void onShow(MediationType type, String name, String extra, Info info) {

      }
      // When you must give a reward after a rewarded-video
      @Override public void onReward(@NonNull MediationType type, @NonNull String name, @NonNull String extra) {

      }
    });
```


You can enable loggin to check what is happening

```java
LabCaveMediation.INSTANCE.setLogging(true);
```

To check if the integration of each thirparty is correct open the test module, you must call the "Init" method first and wait till the "onInit" delegate method is called:

```java
*Make sure you remove this test module on your release build.

LabCaveMediation.INSTANCE.initTest(this, "YOUR_API_HASH");
```
If you use proguard add these rules:

```java
-keep class com.androidnative.** { *; }
-keep class com.google.analytics.** { *; }
-keep class com.google.unity.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.google.android.ads.** { *; }
-keep class com.vungle.** { *; }
-keep public class com.labcave.** { *; }
-keep class com.tapdaq.** { *; }
-keep class com.nerd.** { *; }
-keepclassmembers class * { @android.webkit.JavascriptInterface <methods>; }
-keepclassmembers class com.adcolony.sdk.ADCNative** { *;}
-keep class com.chartboost.** { *; }
-keep class com.oneaudience.** { *; }
-keepattributes SourceFile,LineNumberTable
-keepattributes JavascriptInterface
-keep class android.webkit.JavascriptInterface { *;}
-keep class com.unity3d.ads.** {*;}
-keep class com.facebook.** { *; }
-keepclassmembers class com.ironsource.sdk.controller.IronSourceWebView$JSInterface {
    public *;
}
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep public class com.google.android.gms.ads.** {
   public *;
}
-keep class com.ironsource.adapters.** { *;
}
-keep class com.startapp.** {
      *;
}

-keep class com.truenet.** {
      *;
}

-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface
-dontwarn com.startapp.**

-dontwarn org.jetbrains.annotations.**
-dontwarn com.moat.**
-keep class com.moat.** { public protected private *; }
```

### Example

[Sample](./app/src/main/java/com/labcave/labcavemediation/android/sample/MainActivity.java)
