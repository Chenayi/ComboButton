# ComboButton
连击按钮效果

![Alt text](https://github.com/Chenayi/ComboButton/raw/master/Screenshots/1.png)

#### 使用方法:
##### step1. Add the JitPack repository to your build file
````
allprojects {
        repositories {
            ...
        maven { url 'https://jitpack.io' }
    }
}
````

##### step2. Add the dependency
````
dependencies {
	  compile 'com.github.Chenayi:ComboButton:1.0.0'
	}
````

##### How to Use?
````
    <com.cwy.combo.ComboButton
        android:layout_width="match_parent"
        android:layout_height="100dp"
        app:underCircleColor="#000"
        app:centerCircleColor="#333"
        app:topCircleColor="#666"
        app:animDuration="500"
        app:circleSpacing="5dp"
        app:zoomF="1.3"/>
````
