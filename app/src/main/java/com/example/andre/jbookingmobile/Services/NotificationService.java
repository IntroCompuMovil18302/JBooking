package com.example.andre.jbookingmobile.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.andre.jbookingmobile.Entities.Reserva;
import com.example.andre.jbookingmobile.MainActivity;
import com.example.andre.jbookingmobile.MisAlojamientosFragment;
import com.example.andre.jbookingmobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationService extends IntentService {
    FirebaseAuth mAuth;
    DatabaseReference mDataBase;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    int notificationId = 0;
    public Reserva reserva;


    public NotificationService() {
        super("NotificationService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        // Trabajo que debe hacer el servicio
        // Por ahora solo esperar 5 segundos
        Log.i("TAG", "Servicio en ejecución" );
        reserva = (Reserva) intent.getExtras().getSerializable("reserva");
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference("reservas/");
        encontrarReservas(intent);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.je);
        builder.setContentTitle("Nueva Reserva");
        builder.setContentText("Tiene una nueva reserva del alojamiento "+reserva.getAlojamiento().getNombre());
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        Intent intent2 = new Intent(this, MainActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent2, 0);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }

    private void encontrarReservas (final Intent intent) {
        mDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Reserva ireserva = singleSnapshot.getValue(Reserva.class);
                    if (mAuth.getCurrentUser()!=null){
                        Log.i("SER",mAuth.getCurrentUser().getEmail());
                        if (ireserva.getAlojamiento().getAnfitrion().getCorreo().equals(mAuth.getCurrentUser().getEmail())) {
                            Log.i("SER",ireserva.getAlojamiento().getAnfitrion().getCorreo());
                            Log.i("SER",ireserva.getAlojamiento().getNombre());
                            Log.i("SER",reserva.getAlojamiento().getNombre());
                            if (ireserva.getAlojamiento().getNombre().equals(reserva.getAlojamiento().getNombre())){
                                Log.i("SER","NOTIFICACION");
                                createNotificationChannel();
                                createNotification();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase database", "error en la consulta", databaseError.toException());
            }
        });
    }

    /*private void createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            //IMPORTANCE_MAX MUESTRA LA NOTIFICACIÓN ANIMADA
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }*/
}
