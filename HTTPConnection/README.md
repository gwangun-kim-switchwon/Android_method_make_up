# HTTP Connection example
***

## Intro

	안드로이드 어플리케이션이 서버와 통신하기 위한 방법에는 HTTP통신과 Socket 통신 2가지가 있다.
	그 중 정리한 내용은 HTTP통신으로서 URL 접속을 통해 데이터를 읽어오는 방법이다.
	또한 이 정리에서는 HTTPURLConnection를 다룬다.

</br>

## Basis

	안드로이드에서 HTTP통신으로 서버와 통신을 할 때 AsyncTask(비동기)로 통신을 해야한다.
	왜냐하면 안드로이드의 메인쓰레드는 전부 UI 관련 처리를 위해 사용되기 때문이다.
	따라서 비동기 방식을 사용하지 않고 개발자가 임의로 HTTPURLConnection을 하게되면
	Runtime Exception, android.os.NetworkOnMainThreadException이 뜨고
	ANR(Android Not Responding) 상황에 놓이게 될 것이다. 따라서 AsyncTask로 하는 것이가장 마음 편할 것이다.
	* 이번 정리에서는 비동기도 사용하니까 겸사겸사 형태를 파악해두면 마음 편할 것이다.

***

<br/>

### 1. HTTPURLConnection

#### Manifest에 permission 추가하기

***

	HTTP 통신은 인터넷을 사용하게된다. 그렇다면 어플리케이션에서 인터넷을 사용하려면
	INTERNET Permission을 추가해줘야 한다. Manifests->AndroidManifest.xml에 추가해준다.
	<application>태그 밖에 써줘야 함을 잊지 말자.
	
~~~xml
<uses-permission android:name="android:permission.INTERNET"/>
~~~

</br>

### 2. Class 만들어주기

***

	HttpURLConnection에는 POST와 GET 2가지 방식이 존재한다.
	먼저 POST의 경우에는 앱에서 URI를 통해 요청하게 되면 리소스를 생성하게 되는 경우이며, GET은 URL을 통해 접속해 리소스 값을 조회하는 행위이다. 또한 리소스를 조회하고 해당 도큐먼트에 대한 자세한 정보도 가져온다.
	그렇다면 POST의 경우에는 정상적으로 작동이 된다면 리소스 생성에 대한 응답 코드를 보낼 것이며, GET의 경우에는 해당 도큐먼트(ex. 그림파일, DB, etc...)에 대한 정보를 불러오게 됨으로 두 가지 방식에는 약간의 차이가 존재하게 된다. 따라서 POST와 GET에 대한 메소드를 만드는데 약간의 차이점이 존재하게 된다.
	
	
</br>

#### 2.1 POSTFunction

~~~java

public class onHTTPConnection {

    String EXCEPTION_ERROR = "Exception Occured. Check the url";

 		public String POSTFunction(String mUrl, String params) {

			try {
				//받아온 String을 url로 만들어주기
				URL url = new URL(mUrl);

				//conn으로 url connection을 open 해주기
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				//불러오는데 시간이 오래 걸리는 경우 Time out 설정
				conn.setReadTimeout(10000);
				//연결하는데 시간이 오래 걸리는 경우 Time out 설정
				conn.setConnectTimeout(15000);
				//연결 방법 설정
				conn.setRequestMethod("POST");
				conn.setDoInput(true);
				conn.setDoOutput(true);

				//Accept-Charset 설정 UTF-8 or ASCII
				conn.setRequestProperty("Accept-Charset", "UTF-8");
				conn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");

				// POST로 넘겨줄 파라미터 생성.
				byte[] outputInBytes = params.getBytes(StandardCharsets.UTF_8);
				OutputStream os = conn.getOutputStream();
				os.write(outputInBytes);
				os.close();

				//결과값을 받아온다.
				InputStream is = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line;
				StringBuffer response = new StringBuffer();
				while ((line = br.readLine()) != null) {
					response.append(line);
					response.append('\r');
				}
				br.close();

				String res = response.toString();
				res = res.trim();

				return res;

			} catch (Exception e) {
				e.printStackTrace();
			}

			Log.d("ERROR", EXCEPTION_ERROR);
			return null;
		}
	}
}

~~~

</br>

#### 2.2 GETFunction

~~~java
public class onHTTPConnection {

    String EXCEPTION_ERROR = "Exception Occured. Check the url";

		public String GETFunction(String mUrl) {
			try {
				URL url = new URL(mUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(15000);
				conn.setReadTimeout(10000);
				conn.setDoInput(true);
				conn.setDoOutput(true);

				InputStream is = conn.getInputStream();
				StringBuilder sb = new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String result;
				while ((result = br.readLine()) != null) {
					sb.append(result + '\n');
				}

				result = sb.toString();

				return result;

			} catch (Exception e) {
				e.printStackTrace();
			}

			Log.d("ERROR", EXCEPTION_ERROR);
			return null;
		}
	}
}
~~~

</br>

### 3. AsyncTask

***

	앞서 설명했듯이, Http Connection은 스레드를 쓰게된다. 이때 안드로이드의 메인스레드는 UI스레드로서, 온전히 UI를 구성하는데 스레드가 사용된다. 따라서 스레드를 새로 만들어주거나 AsyncTask로 비동기화 시켜서 사용하지 않으면 
	android.os.NetworkOnMainThreadException 에러가 발생하게된다. 따라서 class화 시킨 HttpURLConnection을 사용하려는 java파일에서 비동기를 사용하여 불러와주면 된다.
	
~~~java
    public class HttpFunc extends AsyncTask<Void, Void, Boolean> {
        String url;

        public HttpFunc(String str) {
            this.url = str;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //AsyncTask로 백그라운드 작업을 싱해하기 전에 실행되는 부분
            //Dialog 같은 것을 띄워 놓으면 좋다.
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            onHTTPConnection urlconn = new onHTTPConnection();
            String GET_result = urlconn.GETFunction(this.url);
            String POST_result = urlconn.POSTFunction(this.url, "Params");
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
~~~

</br>

#### AsyncTask 부연설명

***

	
	onPreExecuted() -> AsyncTask로 백그라운드 작업을 실행하기 전에 실행되는 부분이다. 이 부분에는 로딩 중 과 같은 다이얼로그를 띄우는 등 스레드 작업 이전에 수행할 동작을 구현하면 된다.
	
	doInBackground(Void... voids) -> 에서는 실질적으로 진행할 작업을 작성하면 된다. 그 작업이 비동기로 진행되는 것이다.
	
	onPostExecute(Boolean aBoolean) -> doInBackground 작업이 끝난 후 결과 파라미터를 리턴받아오면 실행되는 부분으로서, 비동기 작업이 종료된 이 후 실행될 동작을 구현하면 된다.
	
	onProgressUpdate() -> publishProgress()가 호출 될 때 자동으로 호출되는 메소드이다.
	
	onCancelled() -> 비동기 동작이 취소될 때 실행되는 메소드로서 오류처리 같은 행위를 하면 된다.
	

#### AsyncTask Generic Type에 대한 설명

***

	내가 AsyncTask를 처음 썼을 때 정말 너무 궁금했던 것은 바로 AsyncTask의 Generic Type이었다. 도대체 저 <Void, Void, Boolean>이 뜻하는게 뭔지 몰라서 이거저거 바꿔보고 오류 부분을 보면서 얼핏 파악만 했지 정확한 뜻은 모르고 했었다. 그래서 정리하게 된 김에 열심히 찾아보았다.
	
	* AsyncTask <Params, Progress, Result>
		- Params : doInBackground 파라미터 타입이며, execute의 메소드 인자 값이 된다.
		- Progress : doInBackground 작업 시 진행 단위의 타입으로 onProgressUpdate의 파라미터 타입이다.
		- Result : doInBackground의 리턴값으로 onPostExecute 파라미터 타입이 된다.

	(출처 : https://itmining.tistory.com/7)
	
</br>

### 4. AsyncTask 실행하기
	AsyncTask의 경우에는 onCreate나 onResume에서 execute를 통해 불러오면 된다. 내가 정리한 AsyncTask는 생성자가 있으므로 생성자를 통해서 파라미터값을 넘겨주어 실행하면 된다.
	
~~~java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpFunc httpFunc = new HttpFunc("url");
        httpFunc.execute();

    }
~~~

</br>

### 5. Http가 안된다.

***

	구글 선생님들께서 Http가 기본적으로 안돌아가게 막아두었다. Http로 통신을 하려고 하면 오류가 뜬다. 참 오픈마인드가 없는 양반들이다. 그래도 나는 Http로 통신을 해야 한다면 아래와 같이 진행하자.

#### 5.1 res에 xml폴더 만들어주기

	먼저 res에 xml폴더를 하나 생성해준다. res에 오른쪽 마우스 버튼을 클릭하고 New에서 Android Resource Directory를 누르면 아래 그림과 같은 창이 하나 뜬다. 여기서 맨 아래에 있는 xml를 선택해주고 생성한다.
	
<img src="./markdown/img/new_resource_directory_list.PNG"/>

</br>

#### 5.2 network_security_config.xml 파일생성

	그 다음 xml폴더 안에 network_security_config.xml 파일을 하나 생성해주고 아래 내용을 넣어준다.
	
~~~xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">"원하는 domain넣기"</domain>
    </domain-config>
</network-security-config>
~~~

#### 5.3 AndroidManifest에 등록하기

	폴더와 파일을 생성하고 내용을 넣어주었으면 AndroidManifest.xml에 가서
	application 태그 안에 networkSecurityConfig를 넣어준다.

~~~xml
<application
android:networkSecurityConfig="@xml/network_security_config"
>
~~~
	
## HttpURLConnection 포스팅을 마치며

***

	진짜 솔직히 HttpURLConnection 써야할게 너무 많다. 인간적으로 별로다. Android os 업데이트 되면서 Http를 사용하게 되면 오류나게되서 network_security도 만들어야 하고 정말 너무너무 해도해도 너무하게 복잡하다. 그래도 기본적으로 사용되는 메소드니까 알아두어야 한다. 그래도 그렇지 해도해도 너무한다. 너무 길다.... 그래서 다음에는 okHttp3 포스팅을 할거다. 