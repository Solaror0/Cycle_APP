package com.example.myapaRplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
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
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE); //wakes up device
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "myApp:myWakeLockTag");
        wakeLock.acquire(10*60*500L); //5 minutes  in milliseconds (10 min * 60 seconds converted to ms)


        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show(); //creates a notif
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) { //sets up ringtone manager
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // setting default ringtone
        ringtone = RingtoneManager.getRingtone(context, alarmUri);

        // play ringtone and release wakelock
        ringtone.play();
        wakeLock.release();

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
