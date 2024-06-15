package com.example.myapaRplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
/**
 * Class Title: AlarmReceiver
 * Description: This class handles broadcasting and alarm functionality, working with but not directly connected to the alarmWidget
 * Last Edited: June 18th 2024
 * Author: Jun Nur Mustaqeem
 */
public class AlarmReceiver extends BroadcastReceiver {

    static Ringtone ringtone;
    static String titleText;

    @RequiresApi(api = Build.VERSION_CODES.Q)

    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000); //vibrate service and vibrates the phone


        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show(); //creates a notif
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) { //sets up ringtone manager
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // setting default ringtone
        ringtone = RingtoneManager.getRingtone(context, alarmUri);

        // play ringtone
        ringtone.play();
    }

//    public static void setTitle(String title){
//        this.titleText = title;
//    }
    public static void stopRinging(){
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
            ringtone = null;
        }
    }
}
