--------------
Project brought to you by FTC Team 11093 MegaKnytes

## Installation (MavenLocal)
-------------------
1. Open the **DecisionTable** Project in Android Studio

2. Run the Configuration `DecisionTable [publishToMavenLocal]` to install the DecisionTables Package locally

> **THIS MUST BE DONE ON EVERY  COMPUTER PUSHING CODE**

3. Open your ***FTC SDK*** Project in Android Studio

4. Go to your `build.dependencies.gradle` file

5. Add the following to the top of the `repositories` section

```groovy
repositories {  
    mavenLocal()  
    ...
}
```

6. Go to the bottom of the file and add the following at the end of the `dependencies` section

```groovy
dependencies {  
    implementation 'com.MegaKnytes.DecisionTable:DecisionTable:1.0.0'  
}
```

7. Perform a gradle sync to implement your changes

8. Congrats! You can now use decision tables in your project


## Installation (Normal)
-----------
1. Open your ***FTC SDK*** Project in Android Studio

2. Go to your root  `build.gradle` file *(Project: FtcRobotController)*

3. Add the following to the end of the `repositories` section in `allprojects`

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

4. Go to your `build.dependencies.gradle` file and add the following at the end of  the `dependencies` section

```groovy
dependencies {  
    implementation 'com.hccsarobotics:DecisionTable:master-SNAPSHOT'  
}
```

5. Perform a gradle sync to implement your changes

6. Congrats! You can now use decision tables in your project