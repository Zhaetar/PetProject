package jessicamatheus.petproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by udesc on 23/10/2017.
 */


//Activity, Intent e Context
public class PetAcceptInterface extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petinterface);

        showAlertDialog();
        }//onCreate

    public void sendToast(String message){Toast.makeText(PetAcceptInterface.this, message, Toast.LENGTH_SHORT).show();}

    public void exitApp(){
        Intent intent = new Intent(PetAcceptInterface.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(PetAcceptInterface.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(PetAcceptInterface.this);
        }
        builder.setTitle("Pet Informaton")
                .setMessage("PLACEHOLDERS")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendToast("Seu mascote ficou feliz!");
                        Bundle b = new Bundle();
                        int xp = 75;
                        b.putInt("xp", xp);

                        Intent intent = new Intent(PetAcceptInterface.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendToast("Seu mascote ficou triste! :(");
                        exitApp();
                    }
                })
                .setIcon(R.drawable.paw_badge)
                .show();
    }
}