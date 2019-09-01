import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpFunc httpFunc = new HttpFunc("url");
        httpFunc.execute();

    }

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
            String POST_result = urlconn.POSTFunction(this.url, this.url);
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
}
