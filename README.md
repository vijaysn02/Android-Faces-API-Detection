## Objective:

-> Identify Face in Image 
-> Tracking Face in Camera feed 
-> Identifying the Facial Landmarks
-> Layer over the Facial Landmark

Referred Tutorial Link - https://hub.packtpub.com/building-an-android-app-using-the-google-faces-api-tutorial/

--------------------------------------------------------------------------------------------------------------------------------

## Mobile Vision:

-> Detect objects in Image / Video Frame

--------------------------------------------------------------------------------------------------------------------------------

## Faces API Concepts:

	• Face tracking extends face detection to video sequences. When a face appears in a video for any length of time, it can be identified as the same person and can be tracke
	• A landmark is a point of interest within a face. The left eye, right eye, and nose base are all examples of landmarks. The Face API provides the ability to find landmarks on a detected face.
	• Classification is determining whether a certain facial characteristic is present. For example, a face can be classified with regards to whether its eyes are open or closed or smiling or not.
  
--------------------------------------------------------------------------------------------------------------------------------

## Dependencies:

Open up the app module’s build.gradle file and update the dependencies to include the Mobile Vision APIs:

```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation"org.jetbrains.kotlin:kotlin-stdlib-jre7:$kotlin_version"
    implementation 'com.google.android.gms:play-services-vision:11.0.4'
    ...
}
```

Now, update your AndroidManifest.xml to include meta data for the faces API:

```
<meta-data
 android:name="com.google.android.gms.vision.DEPENDENCIES"
 android:value="face" />
 ```


--------------------------------------------------------------------------------------------------------------------------------

## Process:

-> Load Image / Video Frame into the Memory.
-> Get a paint Instance.
-> Create temporary bitmap from the image. 
-> Create a canvas with bitmap image for drawing surfaces.
-> Create Face Detector and process the bitmap image.
-> Get an Array of Face Objects.
-> Draw layout on Facial Landmark
-> Update UI
-> Release the Face Detector Instance
