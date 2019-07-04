package vn.com.it.truongpham.dowloadintentsevice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LocalWordSevice extends Service {

    private final  IBinder mBinder = new MyBinder();
    private List<String> resultList=new ArrayList<>();
    private int counter=1;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        addResultValues();
        return Service.START_NOT_STICKY;
    }

    public List<String> getWordList() {
        return resultList;
    }

    @Override
    public IBinder onBind(Intent intent) {
        addResultValues();
        return mBinder;
    }
    public void addResultValues(){
        Random random=new Random();
        List<String> input=Arrays.asList("Linux", "Android","iPhone","Windows7" );
        resultList.add(input.get(random.nextInt(3)) + " " +counter++);
        if(counter== Integer.MAX_VALUE){
            counter=0;
        }
    }
    public class MyBinder extends Binder{
        LocalWordSevice getSevice(){
            return LocalWordSevice.this;
        }
    }
}
