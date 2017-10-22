package jessicamatheus.petproject;

import android.content.SharedPreferences;
import android.widget.Button;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by Zhaetar on 22/10/2017.
 */

public class PetInfo extends HomeActivity{
    public int Level = 1;
    public int Experience = 0;

    public void refreshLevel(){
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
                ProgressBar.setProgressWithAnimation(Experience);};
        }
        final Button levelButton = (Button) findViewById(R.id.level_button);
        levelButton.setText(""+Level);
        saveLevelData();
    }
    public void loadLevelData(){
        //Checar se tem nivel antigo, e se tiver carregar ele
        SharedPreferences savedLevel = getSharedPreferences("LEVEL", 0);
        Level = savedLevel.getInt("LEVEL", 1);
        refreshLevel();
        //Load Experience Value
        SharedPreferences savedExperience = getSharedPreferences("LEVEL", 0);
        Experience = savedLevel.getInt("EXPERIENCE", 0);
        CircularProgressBar ProgressBar = (CircularProgressBar) findViewById(R.id.level_progress);
        ProgressBar.setProgress(Experience);

        final Button levelButton = (Button) findViewById(R.id.level_button);
        levelButton.setText(""+Level);

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
}
