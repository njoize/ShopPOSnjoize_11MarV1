package njoize.dai_ka.com.demotestprint;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class OrderThread extends AsyncTask<String, Void, String> {

    private Context context;

    public OrderThread(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("user", strings[0])
                    .add("numcus", strings[1])
                    .add("billtype", strings[2])
                    .add("tid", strings[3])
                    .add("pid", strings[4])
                    .add("price", strings[5])
                    .add("amount", strings[6])
                    .build();

            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[7]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
