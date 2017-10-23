package jessicamatheus.petproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Zhaetar on 23/10/2017.
 */

public class DebugInterface extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_interface); //Aonde começa o app.

        final HomeActivity HA = new HomeActivity();

        final Button testButton1 = (Button) findViewById(R.id.testButton1);
        testButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Ao clicar no botão..
                HA.sendBasicNotification(v, "Acaricie seu mascote", "Ele precisa do seu carinho!", 001); //Manda a notificação
            }
        });
        final Button testButton2 = (Button) findViewById(R.id.testButton2);
        testButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Ao clicar no botão..
                HA.sendToast("Toast!");
            }
        });
        final Button testButton3 = (Button) findViewById(R.id.testButton3);
        testButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Ao clicar no botão..
                HA.addExperience(20);
            }
        });
        final Button testButton4 = (Button) findViewById(R.id.testButton4);
        testButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Ao clicar no botão..
                setContentView(R.layout.activity_home); //Aonde começa o app.
            }
        });
    }


}
