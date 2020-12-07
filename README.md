Add apply plugin: 'com.huawei.agconnect' 

and



    buildTypes {
        debug {
            signingConfig signingConfigs.config
        }
        release {
            signingConfig signingConfigs.config
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.vip'
        }
    }
      signingConfigs {
        config {
            keyAlias 'keyalias
            keyPassword 'password'
            storeFile file('keypath.jks')
            storePassword 'password'
        }
    }
    
    in module:app
    =========================================================================
    in build.gradle:Project
    buildscript {
    repositories {
        // Configure the Maven repository address for the HMS Core SDK.
        maven {url 'https://developer.huawei.com/repo/'}
    }
    dependencies {
        classpath 'com.huawei.agconnect:agcp:1.4.1.300'
    }
    
    allprojects {
    repositories {
        maven {url 'https://developer.huawei.com/repo/'}
    } } 

    
    
    
 
