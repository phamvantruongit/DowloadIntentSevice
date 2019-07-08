package vn.com.it.truongpham.dowloadintentsevice;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class DowloadSevice extends IntentService {

    private int result = Activity.RESULT_CANCELED;
    public static final String URL = "urlpaht";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "vn.com.it.truongpham.dowloadintentsevice";

    public DowloadSevice() {
        super("DowloadSevice");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlPath = intent.getStringExtra(URL);
        String fileName = intent.getStringExtra(FILENAME);
        File output = new File(Environment.getExternalStorageDirectory(), fileName);
        if (output.exists()) {
            output.delete();
        }

        InputStream stream = null;
        FileOutputStream fos = null;
        try {
            java.net.URL url = new URL(urlPath);
            stream = url.openConnection().getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            fos = new FileOutputStream(output.getPath());
            int next = -1;
            while ((next = reader.read()) != -1) {
                fos.write(next);
            }

            result = Activity.RESULT_OK;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        publishResults(output.getAbsolutePath(), result);
    }

    private void publishResults(String outputPath, int result) {

//        Intent intent=new Intent(NOTIFICATION);
//        intent.putExtra(FILEPATH,outputPath);
//        intent.putExtra(RESULT,result);
//        sendBroadcast(intent);
        EventBus.getDefault().postSticky(outputPath);

    }
}
