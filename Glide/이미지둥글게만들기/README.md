# Image 둥글게 만들기

	Glide로 불러온 이미지를 둥글게 자르고 싶으면 어떻게 해야할까?
	하다가 이전에 썼던 방법은 역시나 Deprecated가 되서 새로운 방법을 찾아보았다.
	오히려 이전에 썼던 방법보다 훨씬 간단하고 편해져서 쉬워졌다.
	기존에 사용했던 Glide 호출에서 한 줄만 추가해주면 된다.
	
~~~java
public void SlicedImg(Context context, String url, ImageView imv){
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().circleCrop()) //이미지 둥글게 잘라주는 옵션
                .into(imv);
}
~~~

</br>

	위에 .apply(new RequestOptions().circleCrop()) 부분만 추가해주면 
	아래와 같이 둥글게 잘린 이미지를 볼 수 있다.
	
>원본
<img src="./../markdown/img/original_img.png" width="330"/>

>circleCrop() 옵션 적용
<img src="./../markdown/img/circlecrop_img.png" width="330"/>