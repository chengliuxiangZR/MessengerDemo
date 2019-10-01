package com.example.asus.messengerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int MSG_SUM = 0x110;
    private static final String TAG="MainActivity";
    private Messenger mService;
    private Messenger mGetReplyMessenger=new Messenger(new MessengerHandler());
    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SUM:
                    Log.i(TAG,"receive msg from Service:"+msg.getData().getString("reply"));
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"onServiceConnected");
            mService=new Messenger(service);
            Message msg=Message.obtain(null,MSG_SUM);
            Bundle data=new Bundle();
            data.putString("msg","hello,this is client");
            msg.setData(data);
            msg.replyTo=mGetReplyMessenger;
            try {
                mService.send(msg);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG,"Error!Please try again!");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
    public void myClick(View view){
        Intent intent=new Intent();
        intent.setAction("com.example.asus.messengerdemo_2.MessengerService");
        intent.setPackage("com.example.asus.messengerdemo_2");
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }
}
