package jessicamatheus.petproject;

/**
 * Created by Zhaetar on 22/10/2017.
 */


import android.app.Activity;
import android.app.ProgressDialog;

public class ProgressDialogHelper {

    public static ProgressDialog makeProgressDialog(final Activity activity, ProgressDialog pd) {
        pd.incrementProgressBy(50);
        return pd;
    }
}
