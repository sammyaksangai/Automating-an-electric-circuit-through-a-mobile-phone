package com.example.android.project;

import android.support.v7.app.AppCompatActivity;



        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.os.Build;
        import android.os.Bundle;
        import android.telephony.SmsMessage;
        import android.util.Log;
        import android.widget.Toast;





public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MSG = "com.example.android.p2.MESSAGE";
    BroadcastReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle myBundle = intent.getExtras();
                SmsMessage[] messages = null;
                boolean flag = false;
                String strMessage = "";
                String dur  = "";

                if (myBundle != null) {
                    Object[] pdus = (Object[]) myBundle.get("pdus");

                    messages = new SmsMessage[pdus.length];

                    for (int i = 0; i < messages.length; i++) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String format = myBundle.getString("format");
                            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                        } else {
                            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        }
                        Log.e("Originating addr", messages[i].getOriginatingAddress());
                        if (messages[i].getOriginatingAddress().equals("+917020887356")) {


                            //strMessage += "SMS From: " + messages[i].getOriginatingAddress();
                            // strMessage += " : ";
                            strMessage += messages[i].getMessageBody();
                            flag = true;
                            //string phoneno = messages[i].getDisplayOriginatingAddress();


                            //strMessage += "\n";

                        }
                        else
                            break;
                    }
                     if(flag == true){
                        String extract = new String(strMessage);
                        dur = extract.substring(extract.length() - 1);
                        Log.e("SMS", extract);
                        Toast.makeText(context, dur, Toast.LENGTH_SHORT);



               /* Log.e("SMS", strMessage);
                Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();*/
                        Intent intobj = new Intent(context, Main2Activity.class);
                        intobj.putExtra(EXTRA_MSG, dur);
                        finish();
                        startActivity(intobj);
                    }
                }
            } //Onreceive ends here
        };

        registerReceiver(mReceiver,new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

    }
}


