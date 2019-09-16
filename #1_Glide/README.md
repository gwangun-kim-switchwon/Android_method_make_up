# Glide Example

	안드로이드에서 글라이드 사용법을 적어놓은 git 입니다.

### 1. dependencies 추가하기 (앱 수준)
*****
<img src="markdown/img/img_gradle_pick.PNG" width="300" />
<img src="markdown/img/img_gradle_add_implementation.PNG" width="550" />



    * implementation 'com.github.bumptech.glide:glide:4.9.0'
    * annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'
     두 문장을 build.gradle (Module:app)의 dependencies에 추가해주세요!
</br>

### 2. AndroidManifest.xml에 INTERNET 사용권한 추가하기

*****

	Glide를 사용할 때 기기 내부의 리소스를 불러오는 경우도 있지만
	이미지 URL을 로드해서 보여주고자 하는 경우도 있습니다. 
	URL 로드해서 이미지를 보여주는 경우를 위해 앱에 인터넷 사용 권한을 추가해줍니다.
	아래 문장을 manifests->AndroidManifest.xml에 <application>태그 밖에 작성해줍니다.
	
~~~xml
<uses-permission android:name="android.permission.INTERNET"/>
~~~

<img src="markdown/img/img_manifest_add.PNG"/>


</br>

### 3. 이미지를 담을 ImageView Layout에 추가해주기

*****

	원하는 Layout에 ImageView를 추가해줍니다.
	저는 간단하게 테스트용 이니까 activity_main.xml에 아래와 같이 넣어주었습니다.
~~~xml
	<ImageView
    	android:id="@+id/main_img"
    	android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:srcCompat="@drawable/image_temp"/>
~~~


### 4. .java 파일에서 Glide로 Image 불러와주기

*****

	원하는 Layout의 java파일로 가서 Glide를 통해 이미지를 불러와줍니다.

~~~java
ImageView iv_glide = findViewById(R.id.main_img);
Glide.with(this).load("원하는 이미지의 URL")
                .placeholder(getDrawable(R.drawable.logo))
                .into(iv_glide);
~~~

	|1| .with -> 어떤 View에 넣을 지 정해주는 겁니다.
	|2| .load -> 원하는 이미지의 URL을 넣어주면 됩니다. 만약 내부 리소스를 사용한다면
				 R.drawable.//이미지파일 이름 <- 이런식으로 넣어주면 됩니다.
	|3| .placeholder -> 이미지를 로딩하는동안 처음에 보여줄 placeholder 이미지를 보여줍니다.
	|4| .into -> 어떤 ImageView에 넣어줄지 정하는 겁니다.
	|5| .error -> 이미지 불러오기를 실패할 경우 나타낼 이미지를 표시해줍니다.
	|6| .thumbnail -> 지정한 %비율만큼 미리 이미지를 가져와서 보여줍니다.
					  0.1f로 지정했다면 실제 이미지의 크기 중 10%만 먼저 가져와서 흐릿하게 보여줍니다.
	|7| .asGif -> GIF를 로딩해줍니다.
