import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
