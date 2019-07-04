package vn.com.it.truongpham.dowloadintentsevice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BindSeviceActivity extends AppCompatActivity implements ServiceConnection {
    private LocalWordSevice s;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_sevice);
        listView=findViewById(R.id.listView);

        wordList=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,wordList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=new Intent(this,LocalWordSevice.class);
        bindService(intent,this,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
       LocalWordSevice.MyBinder b= (LocalWordSevice.MyBinder) service;
        s=b.getSevice();
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
       s=null;
    }

    private ArrayAdapter<String> adapter;
    private List<String> wordList;
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.updateList:
                if (s != null) {
                    Toast.makeText(this, "Number of elements" + s.getWordList().get(0),
                            Toast.LENGTH_SHORT).show();
                    wordList.clear();
                    wordList.addAll(s.getWordList());
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.triggerServiceUpdate:
                Intent service = new Intent(getApplicationContext(), LocalWordSevice.class);
                getApplicationContext().startService(service);
                break;
        }
    }
}
