# SettingPreference

***

## Basis

	오늘은 안드로이드 앱을 만들 때 자주 접하게 될 settingPreference에 대하여 정리했다.
	settingPreference는 Activity외에 다른 xml로 setting에 대한 메뉴를 먼저 정의해준다.
	그 정의를 fragment로 불러들여와서 표현하는 형식이 바로 settingPreference다.

</br>

## deprecated

<img src="markdown/img/deprecated_preference.PNG"/>
<img src="markdown/img/deprecated_method.PNG"/>

	내가 쓰던 Preference는 deprecated되었다. API level 29에서 버려졌다. 마음이 아프다.
	그래도 Use the AndroiX Preference Library라고 명시해주며, 대안책을 내놓았다. 갓글
	
 이미지출처 : <a href="https://developer.android.com/reference/android/preference/PreferenceScreen" target="_blank">PreferenceScreen Android reference</a>, 내 노트북 안드로이드 스튜디오
	
	
	
## 1.implementation 추가하기

	기존의 Preference가 deprecated되면서 androidx의 preference를 사용해야 된다.
	이를 위해서는 앱수준의 gradle(build.gradle(Module:app)에 dependencies를 추가해준다. 
	
~~~xml
implementation 'androidx.preference:preference:1.1.0-rc01'
~~~

## 2. setting_preference.xml 만들기

<img src="markdown/img/setting_file.PNG"/>

	사진과 같이 res에 xml 디렉토리를 하나 만들어서 File_name.xml을 만들어준다.
	이 후 원하는 기능을 xml에 추가해주면된다. 여기서는 몇 가지를 정리해서 넣었다.
	
~~~xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.preference.PreferenceScreen
    xmlns:android="http://schemas.android.com/apk/res/android">
    <androidx.preference.PreferenceCategory android:title="설정">

        <androidx.preference.CheckBoxPreference
            android:defaultValue="true"
            android:key="key_chk_box"
            android:summary="Check Box Summary"
            android:title="Check Box Title" />
        <androidx.preference.EditTextPreference
            android:defaultValue="test"
            android:key="key_edit_text"
            android:summary="EditText summary"
            android:title="EditText Title" />

        <androidx.preference.ListPreference
            android:defaultValue="ko"
            android:entries="@array/language"
            android:entryValues="@array/language_value"
            android:key="key_language"
            android:negativeButtonText="@null"
            android:positiveButtonText="@null"
            android:summary="List summary"
            android:title="List Title" />

        <androidx.preference.PreferenceCategory>

            <androidx.preference.CheckBoxPreference
                android:defaultValue="false"
                android:key="key_dependent"
                android:summary="summary"
                android:title="dependency test" />

            <Preference
                android:dependency="key_dependent"
                android:key="key_dependent_child"
                android:summary="test2 summary"
                android:title="dependency test2" />
        </androidx.preference.PreferenceCategory>


    </androidx.preference.PreferenceCategory>


</androidx.preference.PreferenceScreen>
~~~


		
~~~xml
<androidx.preference.PreferenceCategory>

            <androidx.preference.CheckBoxPreference
                android:defaultValue="false"
                android:key="key_dependent"
                android:summary="summary"
                android:title="dependency test" />

            <Preference
                android:dependency="key_dependent"
                android:key="key_dependent_child"
                android:summary="test2 summary"
                android:title="dependency test2" />
        </androidx.preference.PreferenceCategory>
~~~
	이 부분은 CheckBox가 체크되면 활성화 되는 부분으로서, dependency 속성이 추가된다.
	
### 2.1 ListPreference 리스트 추가하기