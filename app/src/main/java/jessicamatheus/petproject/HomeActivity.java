package jessicamatheus.petproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;


public class HomeActivity extends Activity {
    public void sendBasicNotification(View view, String title, String text, int id) {
        PetNotification.notify(this.getApplicationContext(), "Your pet could use some attention!", 1);
    }//Fim do Send Notification
    public void sendToast(String texto){
        Toast.makeText(HomeActivity.this, texto, Toast.LENGTH_LONG).show();
    }//Fim do Send Toast

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); //Aonde começa o app.

        final PetInfo Pet = new PetInfo();
        final PhotoImport Photo = new PhotoImport();

        //Carrega as variaveis.
        Pet.loadLevelData();
        Photo.loadImageData();

        //Checar se tem foto antiga, e se tiver colocar ela.
        //Coloca o texto no botão do nivel

        //Botão de teste.
        final Button levelButton = (Button) findViewById(R.id.level_button);
        levelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Ao clicar no botão..
                //setContentView(R.layout.petinterface); //Aonde começa o app.
                //sendBasicNotification(v, "Acaricie seu mascote", "Ele precisa do seu carinho!", 001); //Manda a notificação
                //sendToast("Notificação criada com sucesso.");
                Pet.addExperience(30);
            }
        });

        //Foto clicavel para trocar de foto
        final de.hdodenhof.circleimageview.CircleImageView petPicture =
                (de.hdodenhof.circleimageview.  CircleImageView) findViewById(R.id.pet_round_image);
        petPicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Photo.pickImage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }//Fim do inicio do sistema.

    @Override
    //Quando volta pro app com algum dado, essa função que manuseia a imagem.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoImport.PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                sendToast("Erro ao selecionar uma imagem.");
                return;
            }
            Uri uri = data.getData();

            Bitmap bitmap = null;
            try {bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {e.printStackTrace();}
            
            PhotoImport Photo = new PhotoImport();
            Photo.setPetImage(bitmap);
        }
    }//Fim do onActivityReult

}//Fim do Arquivo.
