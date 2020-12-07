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
 
