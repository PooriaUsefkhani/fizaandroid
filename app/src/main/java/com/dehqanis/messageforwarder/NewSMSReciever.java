package com.dehqanis.messageforwarder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewSMSReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            Log.d("SMSSTUFF","NEW MSG");

            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
                Log.d("SMSSTUFF",smsMessage.getMessageBody());
                APIHandler.getAPI().sendSMS(smsMessage.getOriginatingAddress(),messageBody).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.d("SMSSTUFF","server success");
                        }else {
                            Log.d("SMSSTUFF","server fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }

    }

}
