/*package jessicamatheus.petproject;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by Zhaetar on 22/10/2017.
 */
/*
public class Zhaetar extends HomeActivity {
    public static final int PICK_PHOTO = 1;
    public Boolean CUSTOM_PHOTO_EXISTS = false;
    public int Level = 1;
    public int Experience = 0;

    public static void updateLevel() throws InterruptedException {
        Experience += 30;
        CircularProgressBar ProgressBar = (CircularProgressBar) findViewById(R.id.level_progress);
        if (Experience < 100)
            ProgressBar.setProgressWithAnimation(Experience, 500);
        else
            ProgressBar.setProgressWithAnimation(100, 500);
        while (Experience >= 100) {
            Level++;
            ProgressBar.setProgressWithAnimation(0);
            Experience -= 100;
            if (Experience > 0) {
                Thread.sleep(1000);
                ProgressBar.setProgressWithAnimation(Experience);
            }
            ;
        }
        final Button levelButton = (Button) findViewById(R.id.level_button);
        levelButton.setText("" + Level);

        //Salva os dados.
        SharedPreferences saveLevel = getSharedPreferences("LEVEL", 0);
        SharedPreferences.Editor mEditor = saveLevel.edit();
        mEditor.putInt("LEVEL", Level).commit();

        SharedPreferences saveExperience = getSharedPreferences("EXPERIENCE", 0);
        mEditor = saveExperience.edit();
        mEditor.putInt("EXPERIENCE", Experience).commit();
    }
    public int getLevel(){return Level;}
    public int getExperience(){return Experience;}
}*/