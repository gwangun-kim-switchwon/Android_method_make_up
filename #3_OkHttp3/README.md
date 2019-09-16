# OkHttp3

***

## Intro

	어제 HttpURLConnection에 대하여 정리를 했다. GET이든 POST든 뭘 하든
	너무 길고 복잡해서 잘 안쓴다 나도. 그래서 오늘은 쉽고 편한 OkHttp3에 대하여 정리한다.
	
## Basis


	OkHttp3는 Square사의 오픈소스 라이브러리이다. OkHttp는 Retrofit2에서도 이용되는데,
	Retrofit2가 기본적으로 OkHttp를 네트워킹 계층으로 활용한다. 그만큼 유용한 라이브러리이다.
	OkHttp도 네트워크 통신이므로 메인스레드와 구분하여 처리해주는 것은 동일하지만 말이다.


## 1. Gradle에 OkHttp 추가하기


	manifests->AndroidManifest.xml에 okHttp를 implementation 해준다.

~~~xml
implementation 'com.squareup.okhttp3:okhttp:3.10.0'
~~~

## 2. class 만들어주기
	
	HttpURLConnection을 정리할때도 그렇지만 네트워크 통신을 하는 경우에는 class를 따로
	하나 만들어서 필요할 때마다 call하는 방식이 너무 편하다.
	의외로 네트워크 통신을 쓰는 경우도 많고 이미 만든거 계속 활용하는게 더 편하기 때문이다.
	
~~~java
public class OkHttpConnection {

    private String RETURN_STATEMENT_ERROR = "ERROR has occured";

    Context context;

    public OkHttpConnection(Context context) {
        this.context = context;
    }

    public String GETFunc(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("name", "value")
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("ERROR", RETURN_STATEMENT_ERROR);
        return RETURN_STATEMENT_ERROR;
    }

    public String POSTFunc(String url, String json) {
        try {
            OkHttpClient client = new OkHttpClient();

            //JSON 형태로 POST한다고 가정
            final MediaType JSON = MediaType.parse("appliation/json; charset=utf-8");

            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSON, json))
                    .build();

            Response response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("ERROR", RETURN_STATEMENT_ERROR);

        return RETURN_STATEMENT_ERROR;
    }

}

~~~

## 3. AsyncTask로 메소드 콜 해줘서 사용하기
	이 부분은 따로 예제 코드를 안넣겠다. HttpURLConnection에서 
	AsyncTask를 썼으니까 그 부분을 참고해서 쓰면 될 것이다. 
	이미 class에서 메소드를 정의할 때 execute까지 했으니 그냥 call만 해주면 된다.
	
<a href="https://github.com/Uni-Stark/Android_method_make_up/blob/master/HTTPConnection/Code/HttpCall.java" target="_blank">AsyncTask로 메소드 콜 사용법</a>
	
## OkHttp3 포스팅을 마치며

	너무도 짧고 아름답다. HttpURLConnection으로 별거별거를 다 하던걸 단 몇줄만으로 끝냈다.
	진짜 최고다 OkHttp3.... 나중에는 Retrofit2도 정리를 할 예정이다.
	다만 HttpConnection과 OkHttp3 두 가지를 동시에 하니까 다른게 하고싶어서 아직 안할거다.
	
#### 참고 : <a href="https://square.github.io/okhttp/" target="_blank">https://square.github.io/okhttp/</a>
