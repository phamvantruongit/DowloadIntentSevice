package vn.com.it.truongpham.dowloadintentsevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(DowloadSevice.FILEPATH);
                int result = bundle.getInt(DowloadSevice.RESULT);
                if (result == RESULT_OK) {
                    Toast.makeText(MainActivity.this, "Download done" + string, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Download failed", Toast.LENGTH_SHORT).show();

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(receiver, new IntentFilter(DowloadSevice.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(receiver);
    }

    public void Dowload(View view) {

        Intent intent = new Intent(this, DowloadSevice.class);
        intent.putExtra(DowloadSevice.FILENAME, "index.html");
        intent.putExtra(DowloadSevice.URL,
                "https://www.vogella.com/index.html");
        startService(intent);

    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void OnCustomEvent(String message){
        Toast.makeText(this, message + "&&&&", Toast.LENGTH_SHORT).show();
    }
    
}
