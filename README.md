LabCave Mediation Android SDK
=====
The current version has been tested using API 21, 23, 25 and 26 and require a minSdkVersion >= 17

## Download SDK

Download our SDK, unzip the file and add all files in lib folder to your project's lib folder. Then add these lines to your build.gradle:

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
implementation(name: 'labcavemediation-base-2.9.1', ext: 'aar')
implementation fileTree(dir: 'libs/libs', include: ['*.jar'])
implementation 'com.google.android.gms:play-services-base:+'
```

**Recommended:**

```java
dependencies {
    implementation 'com.android.support:multidex:1.0.2'
}
```

**Make sure you add the following depending on your selected Ad Networks**

**AdColony**

```java
dependencies {
    implementation(name: 'labcavemediation-mediation-adcolony-fat-2.9.1', ext: 'aar')
    implementation 'com.google.android.gms:play-services-ads:+'
}
```
**Admob**

```java
dependencies {
    implementation(name: 'labcavemediation-mediation-admob-2.9.1', ext: 'aar')
    implementation 'com.google.android.gms:play-services-ads:+'
    implementation 'com.google.android.ads.consent:consent-library:1.0.6'
}
```
Add to your manifest file : 

````java
 <meta-data
        android:name="com.google.android.gms.ads.APPLICATION_ID"
        android:value="YOUR_ADMOB_APPLICATION_ID"/>
````

**AppLovin**

```java
dependencies {
    implementation(name: 'labcavemediation-mediation-applovin-fat-2.9.1', ext: 'aar')
    implementation(name:'recyclerview-v7', ext:'aar')
    implementation 'com.google.android.gms:play-services-ads:+'
}
```

**Chartboost**

```java
dependencies {
    implementation(name: 'labcavemediation-mediation-chartboost-fat-2.9.1', ext: 'aar')
    implementation 'com.google.android.gms:play-services-ads:+'
}
```

**Ironsource**

```java
dependencies {
    implementation(name: 'labcavemediation-ironsource-ironsource-fat-2.9.1', ext: 'aar')
    implementation 'com.google.android.gms:play-services-ads:+'
}
```

**Facebook**

```java
dependencies {
    implementation(name: 'labcavemediation-mediation-facebook-2.9.1', ext: 'aar')
    implementation(name: 'AudienceNetwork', ext: 'aar')
    implementation 'com.google.android.gms:play-services-ads:+'
}
```
**UnityAds**

```java
dependencies {
    implementation(name: 'labcavemediation-mediation-unityads-fat-2.9.1', ext: 'aar')
}
```
**Vungle**

```java
dependencies {
    implementation(name: 'labcavemediation-mediation-vungle-fat-2.9.1', ext: 'aar')
    implementation 'com.google.android.gms:play-services- ads:+'
}
```

## Integrate SDK

Once you have added all the files and the configuration to your build .gradle, it's time to initialize the SDK. 

**Important**: you have to initialize the SDK at the begining of the execution of your app. Make sure you **initiliase it only once**.

The SDK Initialisation can be done in two ways:

1. Initialize each Ad Format separately at different points of the game. ***Recommended*** to minimise the number of ads preloaded without showing an impression.

You can bundle them in the same method

```java
LabCaveMediation.init(this, APP_HASH, LabCaveMediation.AD_FORMAT.BANNER, LabCaveMediation.AD_FORMAT.INTERSTITIAL);
```

Or each of them separately

```java
LabCaveMediation.init(this, APP_HASH, AD_FORMAT.BANNER);

LabCaveMediation.init(this, APP_HASH, AD_FORMAT.INTERSTITIAL);

LabCaveMediation.init(this, APP_HASH, AD_FORMAT.REWARDED_VIDEO);
```

2. Alternatively, you can initiliase ALL of them at the same time with this method
```java
LabCaveMediation.init(this, APP_HASH);
```


The appHash is the hash ID of your app, you can get it in https://mediation.labcavegames.com/panel/apps, "context" is your activity context.

When you init the SDK then you can show ads. 

**Important: the mediation SDK auto fetch all ads for you**, when you call the init method also will fecth the first ads, so you only need to call the showMethod. Display ads with the corresponding action according to the ad format desired:

```java
LabCaveMediation.showBanner(context, tag);
LabCaveMediation.showBanner(labcaveBannerView, tag);

LabCaveMediation.showInterstitial(context, tag);

LabCaveMediation.showRewardedVideo(context, tag);
```

You have to pass the context of your activity and the ad placement where the ad will be shown "double-coins","main-menu", "options", etc. It can also be an empty string but we recommend you to always define an ad placement. 

**The ad placements are automatically created on the dashboard and will appear after the first call of that specific ad placement is done.**


If Banner ads are used, it is recommended to pause/resume ads with the onPause/onResume
method:

```java
@Override protected void onPause() {
    super.onPause();
    LabCaveMediation.pause();
}

@Override protected void onResume() {
    super.onResume();
    LabCaveMediation.resume();
}
```
The position **TOP** or **BOTTOM** and the size SMART(SCREEN_SIZEx50) or BANNER (320x50) can be set at the beggining of the execution or when you call "showBanner":

```java
LabCaveMediation.showBanner(context, tag, BannerPosition bannerPosition);
LabCaveMediation.showBanner(context, tag, BannerSize bannerSize);
LabCaveMediation.showBanner(context, tag, BannerPosition bannerPosition,  BannerSize bannerSize);

LabCaveMediation.showBanner(labcaveBannerView, tag, BannerPosition bannerPosition);
LabCaveMediation.showBanner(labcaveBannerView, tag, BannerSize bannerSize);
LabCaveMediation.showBanner(labcaveBannerView, tag, BannerPosition bannerPosition, BannerSize bannerSize);
```


### Advance integration

The SDK offers a delegate where you can receive the events of the ads. **Important** the method "addListener" add a new listener, so make sure you **do not add the same listener more than once.** 

The SDK will call all the listener added. You can remove a listener or all listeners added.

```java

 LabCaveMediation.removeListener(@NonNull LabCaveMediationListener listener);

 LabCaveMediation.clearListener();

 LabCaveMediation.addListener(new LabCaveMediationListener() {
      // When the SDK is already initialized, if everything is ok, state will be true.
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
      // When you have to give a reward after a rewarded-video
      @Override public void onReward(@NonNull MediationType type, @NonNull String name, @NonNull String extra) {

      }
    });
```

You can enable logging to get additional information by using the following method:

```java
LabCaveMediation.setLogging(true);
```

In order to check if the integration of each thirdparty is correct, open the test module, you have to call the "Init" method first and wait till the "onInit" delegate method is called:

```java
LabCaveMediation.initTest(this, "YOUR_API_HASH");
```

>**Make sure you remove this test module on your release build.**

If you use proguard add these rules:

```groovy
-keep class com.google.analytics.** { *; }
-keep class com.google.unity.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.google.android.ads.** { *; }
-keep public class com.labcave.** { *; }
-keep class com.nerd.** { *; }
#AdColony
-keepclassmembers class * { @android.webkit.JavascriptInterface <methods>; }
-keepclassmembers class com.adcolony.sdk.ADCNative** { *;}

-keep class com.chartboost.** { *; }
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
-dontwarn com.ironsource.mediationsdk.**
-dontwarn com.ironsource.adapters.**
-dontwarn com.moat.**
-keep class com.moat.** { public protected private *; }

#UnityAds
-keepattributes SourceFile,LineNumberTable
-keepattributes JavascriptInterface
-keep class android.webkit.JavascriptInterface {*;}
-keep class com.unity3d.ads.** {*;}
-keep class com.unity3d.services.** {*;}
-dontwarn com.google.ar.core.**
-dontwarn com.unity3d.services.**

#Vungle
-keep class com.vungle.warren.** { *; }
-dontwarn com.vungle.warren.error.VungleError$ErrorCode
# Moat SDK
-keep class com.moat.** { *; }
-dontwarn com.moat.**
# Okio
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Retrofit
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Google Android Advertising ID
-keep class com.google.android.gms.internal.** { *; }
-dontwarn com.google.android.gms.ads.identifier.**
```

### Example

[Sample](./app/src/main/java/com/labcave/labcavemediation/android/sample/MainActivity.java)
