package jessicamatheus.petproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Zhaetar on 22/10/2017.
 */

public class PhotoImport extends HomeActivity {

    public static final int PICK_PHOTO = 1;
    public Boolean CUSTOM_PHOTO_EXISTS = false;

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
            try {dest.flush();
            } catch (IOException e) {e.printStackTrace();}
            try {dest.close();
            } catch (IOException e) {e.printStackTrace();}

        } else{return;} //Caso não tenha permissão, voltar.
    }//Fim do Save Image.
}
