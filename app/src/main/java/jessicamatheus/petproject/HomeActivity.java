package jessicamatheus.petproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class HomeActivity extends Activity {
    //Variaveis
    public static final int PICK_PHOTO = 1;
    public Boolean CUSTOM_PHOTO_EXISTS = false;
    public int Level = 1;
    public int Experience = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); //Aonde começa o app.

        //Checar se tem nivel antigo, e se tiver carregar ele
        SharedPreferences savedLevel = getSharedPreferences("LEVEL", 0);
        Level = savedLevel.getInt("LEVEL", 1);
        //Load Experience Value
        SharedPreferences savedExperience = getSharedPreferences("LEVEL", 0);
        Experience = savedLevel.getInt("EXPERIENCE", 0);
        CircularProgressBar ProgressBar = (CircularProgressBar) findViewById(R.id.level_progress);
        ProgressBar.setProgress(Experience);
        //Checar se tem foto antiga, e se tiver colocar ela.
        SharedPreferences savedPhoto = getSharedPreferences("CUSTOM_PHOTO_EXISTS", 0);
        Boolean CUSTOM_PHOTO_EXISTS = savedPhoto.getBoolean("CUSTOM_PHOTO_EXISTS", false);

        //Coloca o texto no botão do nivel
        final Button levelButton = (Button) findViewById(R.id.level_button);
        levelButton.setText(""+Level);

        if(CUSTOM_PHOTO_EXISTS){
             File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/PetPet/Cache/Pet.JPEG");
            String filePath = file.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);

            ImageView imageView = (ImageView) findViewById(R.id.pet_round_image);
            imageView.setImageBitmap(bitmap);
        }

        //Botão de teste.
        levelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Ao clicar no botão..
                //setContentView(R.layout.petinterface); //Aonde começa o app.
                //sendBasicNotification(v, "Acaricie seu mascote", "Ele precisa do seu carinho!", 001); //Manda a notificação
                //sendToast("Notificação criada com sucesso.");
                try {
                    levelUp();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        PetNotification.notify(this.getApplicationContext(), "Your pet could use some attention!", 1);
    }//Fim do Send Notification

    public void sendToast(String texto){
        Toast.makeText(HomeActivity.this, texto, Toast.LENGTH_LONG).show();
    }//Fim do Send Toast

    public void levelUp() throws InterruptedException {
        Experience += 30;
        CircularProgressBar ProgressBar = (CircularProgressBar) findViewById(R.id.level_progress);
        if(Experience < 100)
            ProgressBar.setProgressWithAnimation(Experience,500);
        else
            ProgressBar.setProgressWithAnimation(100,500);
        while(Experience>=100){
            Level++;
            ProgressBar.setProgressWithAnimation(0);
            Experience -= 100;
            if(Experience > 0){
                Thread.sleep(1000);ProgressBar.setProgressWithAnimation(Experience);};
        }
        final Button levelButton = (Button) findViewById(R.id.level_button);
        levelButton.setText(""+Level);

        SharedPreferences saveLevel = getSharedPreferences("LEVEL", 0);
        SharedPreferences.Editor mEditor = saveLevel.edit();
        mEditor.putInt("LEVEL", Level).commit();

        SharedPreferences saveExperience = getSharedPreferences("EXPERIENCE", 0);
        mEditor = saveExperience.edit();
        mEditor.putInt("EXPERIENCE", Experience).commit();
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
