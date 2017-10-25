package jessicamatheus.petproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class HomeActivity extends Activity{
    public int Level = 1;
    public int Experience = 0;
    public static final int PICK_PHOTO = 1;
    public Boolean CUSTOM_PHOTO_EXISTS = false;

    public void sendBasicNotification(View view, String title, String text, int id) {
        }//Fim do Send Notification
    public void sendToast(String texto){
        Toast.makeText(HomeActivity.this, texto, Toast.LENGTH_LONG).show();
    }//Fim do Send Toast

    public void showAlertDialog(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(HomeActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(HomeActivity.this);
        }
        builder.setTitle("Pet Informaton")
                .setMessage("Do you accept the notification?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        addExperience(28);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(R.drawable.paw_badge)
                .show();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); //Aonde começa o app.

        //Verificação do Alert
        Intent intent = getIntent();
        int checkAlertDialog = intent.getIntExtra("showAlertDialog", 0);
        if(checkAlertDialog == 5) {showAlertDialog();}

        loadLevelData();
        loadImageData();

/*
        }*/

        //Botão de teste.
        final Button levelButton = (Button) findViewById(R.id.level_button);
        levelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Ao clicar no botão..
                //sendBasicNotification(v, "Acaricie seu mascote", "Ele precisa do seu carinho!", 000); //Manda a notificação
                Context context;
                context = getApplicationContext();
                PetNotification.notify(context, "Pet Pet", 1);
                //sendToast("Notificação criada com sucesso.");
                //setContentView(R.layout.petinterface);
                /*Intent intent = new Intent(HomeActivity.this, PetAcceptInterface.class);
                startActivity(intent)*/
                //showAlertDialog();
            }
        });

        //Foto clicavel para trocar de foto
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

    @Override
    //Quando volta pro app com algum dado, essa função que manuseia a imagem.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {return;}
            Uri uri = data.getData();

            Bitmap bitmap = null;
            try {bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {e.printStackTrace();}

            setPetImage(bitmap);
        }
        else {sendToast("Erro ao receber arquivo.");}
    }//Fim do onActivityReult

    ///Classes de petInfo
    public void refreshLevel(){
        final CircularProgressBar ProgressBar = (
                CircularProgressBar) findViewById(R.id.level_progress);
        int color = 1;

        if(Experience < 100)
            ProgressBar.setProgressWithAnimation(Experience);

        while(Experience>=100){
            Level++;
            ProgressBar.setProgress(0);
            Experience -= 100;
            if(Experience > 0){

                ProgressBar.setProgressWithAnimation(Experience);
            };
        }

        saveLevelData();
        final Button levelButton = (Button) findViewById(R.id.level_button);
        levelButton.setText(""+Level);
    }

    public void loadLevelData(){
        SharedPreferences savedLevel = getSharedPreferences("LEVEL", 0);
        Level = savedLevel.getInt("LEVEL", 1);

        SharedPreferences savedExperience = getSharedPreferences("EXPERIENCE", 0);
        Experience = savedExperience.getInt("EXPERIENCE", 0);

        final Button levelButton = (Button) findViewById(R.id.level_button);
        levelButton.setText(""+Level);

        CircularProgressBar ProgressBar = (CircularProgressBar) findViewById(R.id.level_progress);
        ProgressBar.setProgress(Experience);
    }

    public void saveLevelData(){
        SharedPreferences saveLevel = getSharedPreferences("LEVEL", 0);
        SharedPreferences.Editor mEditor = saveLevel.edit();
        mEditor.putInt("LEVEL", Level).apply();

        SharedPreferences saveExperience = getSharedPreferences("EXPERIENCE", 0);
        mEditor = saveExperience.edit();
        mEditor.putInt("EXPERIENCE", Experience).apply();
    }

    public void addExperience(int value){
        Experience += value;
        refreshLevel();
    }

///// CLASSES DE FOTO
    public void setPetImage(Bitmap bitmap){
        ImageView imageView = (ImageView) findViewById(R.id.pet_round_image);
        imageView.setImageBitmap(bitmap);

        saveImageOnStorage(bitmap, this);
        CUSTOM_PHOTO_EXISTS = true;
        saveImageData();
    }

    public void loadImageData(){
        SharedPreferences savedPhoto = getSharedPreferences("CUSTOM_PHOTO_EXISTS", 0);
        Boolean CUSTOM_PHOTO_EXISTS = savedPhoto.getBoolean("CUSTOM_PHOTO_EXISTS", false);

        //Se ela existir
        if(CUSTOM_PHOTO_EXISTS){
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/PetPet/Cache/Pet.JPEG");
            String filePath = file.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);

            ImageView imageView = (ImageView) findViewById(R.id.pet_round_image);
            imageView.setImageBitmap(bitmap);
        }
    }

    public void saveImageData(){
        SharedPreferences mPrefs = getSharedPreferences("CUSTOM_PHOTO_EXISTS", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean("CUSTOM_PHOTO_EXISTS", CUSTOM_PHOTO_EXISTS).apply();
    }

    public void pickImage() throws InterruptedException {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO);
    }//Fim do Pick Image (// TODO: 22/10/2017 adicionar a opção para usar outros aplicativos além da galeria.

    public static void saveImageOnStorage(Bitmap finalBitmap, Activity Activity) {
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
            try {
                assert dest != null;
                dest.flush();
            } catch (IOException e) {e.printStackTrace();}
            try {dest.close();
            } catch (IOException e) {e.printStackTrace();}
        }
    }//Fim do Save Image.

}//Fim do Arquivo.
