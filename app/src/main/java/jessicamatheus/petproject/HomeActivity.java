package jessicamatheus.petproject;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); //Aonde começa o app.

        //// TODO: 20/10/2017 criar clsse para level
        //Nivel
        int levelnumb = 0;
        levelnumb += 3;
        //Coloca o texto no botão do nivel
        //Coloca o texto no botroundIConão do nivel
        Button levelbutton = (Button) findViewById(R.id.level_button);
        levelbutton.setText(""+levelnumb);

        //Criação da notificação
        final NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.paw)
                        .setContentTitle("Acaricie seu mascote")
                        .setContentText("Ele precisa do seu carinho!");

        //Botão clicável
        final Button button = (Button) findViewById(R.id.level_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Ao clicar no botão..
                int mNotificationId = 001; //id da notificação
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE); //pega ela
                mNotifyMgr.notify(mNotificationId, mBuilder.build());//e notifica
            }
        });

}


}