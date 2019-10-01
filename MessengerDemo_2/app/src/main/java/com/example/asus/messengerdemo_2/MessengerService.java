package com.example.asus.messengerdemo_2;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {
    private static final int MSG_SUM = 0x110;
    private static final String TAG="MessengerService";
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SUM:
                    Log.i(TAG,"receiver msg from Client:"+msg.getData().getString("msg"));
                    Messenger client=msg.replyTo;
                    Message replyMessage=Message.obtain(null,MSG_SUM);
                    Bundle bundle=new Bundle();
                    bundle.putString("reply","嗯，你的消息我已经收到，稍后会回复你。");
                    replyMessage.setData(bundle);
                    try {
                        client.send(replyMessage);
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private final Messenger messenger=new Messenger(new MessengerHandler());
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
