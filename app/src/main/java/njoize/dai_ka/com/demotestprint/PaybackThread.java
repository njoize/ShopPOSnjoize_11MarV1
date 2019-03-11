package njoize.dai_ka.com.demotestprint;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class PaybackThread extends AsyncTask<String, Void, String> {

    private Context context;

    public PaybackThread(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("oid", strings[0])
                    .add("tid", strings[1])
                    .add("user", strings[2])
                    .add("payment", strings[3])
                    .add("mid", strings[4])
                    .add("discount", strings[5])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[6]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
