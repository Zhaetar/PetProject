package jessicamatheus.petproject;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class PetNotification {
    private static final String NOTIFICATION_TAG = "Pet";

    public static void notify(final Context context,
                              final String exampleString, final int number) {
        final Resources res = context.getResources();

        final String ticker = exampleString;
        final String title = res.getString(
                R.string.pet_notification_title_template, exampleString);
        final String text = res.getString(
                R.string.pet_notification_placeholder_text_template, exampleString);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)

                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.paw_badge)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setTicker(ticker)
                .setNumber(number)
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(context, HomeActivity.class).putExtra("showAlertDialog", 5), PendingIntent.FLAG_UPDATE_CURRENT)
                )
                /*
                .addAction(
                        R.drawable.check_yes2,
                        res.getString(R.string.action_accept),
                        null)
                .addAction(
                        R.drawable.timer,
                        res.getString(R.string.action_deny),
                        null )
                */
                .setAutoCancel(true);

        notify(context, builder.build());
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }

    /**
     * Cancels any notifications of this type previously shown using
     * {@link #notify(Context, String, int)}.
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}
