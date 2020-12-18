package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class DeliveryTimeService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DeliveryTimeService() {
        super("DeliveryTimeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //get the value from checkout activity
        double delivery_time = intent.getDoubleExtra("delivery time", 100);
        try {
            Log.v("stuff", "thread going to sleep");
            //sleep the thread for however long the delivery is
            Thread.sleep((long) delivery_time * 50);
            //build the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "serviceNotification")
                    .setSmallIcon(R.drawable.ic_baseline_fastfood_24)
                    .setContentTitle("Your Delivery is Here!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            //show the notification
            notificationManagerCompat.notify(1, builder.build());
            Log.v("stuff", "notfication sent");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
