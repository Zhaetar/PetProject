package jessicamatheus.petproject;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class HomeActivity extends Activity {
    //Variaveis
    public static final int PICK_PHOTO = 1;
    public Boolean CUSTOM_PHOTO_EXISTS = false;
    public int Level = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); //Aonde começa o app.

        //// TODO: 20/10/2017 criar clsse para level
        //Coloca o texto no botão do nivel
        levelUp();

        //Checar se tem foto antiga, e se tiver colocar ela.
        SharedPreferences mPrefs = getSharedPreferences("CUSTOM_PHOTO_EXISTS", 0);
        Boolean CUSTOM_PHOTO_EXISTS = mPrefs.getBoolean("CUSTOM_PHOTO_EXISTS", false);
        if(CUSTOM_PHOTO_EXISTS){
             File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/PetPet/Cache/Pet.JPEG");
            String filePath = file.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);

            ImageView imageView = (ImageView) findViewById(R.id.pet_round_image);
            imageView.setImageBitmap(bitmap);
        }

        //Botão de teste.
        final Button levelButton = (Button) findViewById(R.id.level_button);
        levelUp();
        levelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Ao clicar no botão..
                sendBasicNotification(v, "Acaricie seu mascote", "Ele precisa do seu carinho!", 001); //Manda a notificação
                sendToast("Notificação criada com sucesso.");
            }
        });

        final de.hdodenhof.circleimageview.CircleImageView petPicture =
                (de.hdodenhof.circleimageview.  CircleImageView) findViewById(R.id.pet_round_image);
        petPicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    pickImage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }//Fim do inicio do sistema.

    public void sendBasicNotification(View view, String title, String text, int id) {
        PetNotification.notify(this.getApplicationContext(), "Your pet could use some attention!", 202);
        /*NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.paw)
                        .setContentTitle(title)
                        .setContentText(text);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());*/
    }//Fim do Send Notification

    public void sendToast(String texto){
        Toast.makeText(HomeActivity.this, texto, Toast.LENGTH_LONG).show();
    }//Fim do Send Toast

    public void levelUp(){
        final Button levelButton = (Button) findViewById(R.id.level_button);
        Level++;
        levelButton.setText(""+Level);
    }


    public void pickImage() throws InterruptedException {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO);

    }//Fim do Pick Image (// TODO: 22/10/2017 adicionar a opção para usar outros aplicativos além da galeria.

    @Override
    //O que isso faz? Ele é o que o programa pega quando volta pro app com alguma data. Então é essa função que
    //manuseia a imagem pega na galeria.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                sendToast("Erro ao selecionar uma imagem.");
                return;
            }
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ImageView imageView = (ImageView) findViewById(R.id.pet_round_image);
                imageView.setImageBitmap(bitmap);

                SaveImage(bitmap, this);
                CUSTOM_PHOTO_EXISTS = true;
                SharedPreferences mPrefs = getSharedPreferences("CUSTOM_PHOTO_EXISTS", 0);
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putBoolean("CUSTOM_PHOTO_EXISTS", CUSTOM_PHOTO_EXISTS).commit();
            } catch (IOException e) {e.printStackTrace();}
        }
        else{
            sendToast("Error!");
            return;
        }
    }//Fim do onActivityReult

    private static void SaveImage(Bitmap finalBitmap, Activity Activity) {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            //Caminho do folder aonde será salvo a imagem.
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                    "/PetPet/Cache/");
            //Vê se exite o folder, se não existir, cria ele.
            if (! mediaStorageDir.exists()){
                if (! mediaStorageDir.mkdirs()){
                    return;}}
            FileOutputStream dest = null;
            //Caminho do folder aonde será salvo a imagem.
            try {dest = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/PetPet/Cache/Pet.JPEG");
            } catch (FileNotFoundException e) {e.printStackTrace();}

            //Salva em JPEG.
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, dest);
            try {dest.flush();
            } catch (IOException e) {e.printStackTrace();}
            try {dest.close();
            } catch (IOException e) {e.printStackTrace();}

        } else{return;} //Caso não tenha permissão, voltar.
    }//Fim do Save Image.
}//Fim do Arquivo.
