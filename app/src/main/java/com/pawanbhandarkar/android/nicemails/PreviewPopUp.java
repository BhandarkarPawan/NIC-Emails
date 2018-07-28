package com.pawanbhandarkar.android.nicemails;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.EditText;

/**
 * Created by Pawan on 27-07-2017.
 */

public class PreviewPopUp extends Activity {

    EditText PreviewET;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previewpopwindow);
        String Email = MainActivity.Email;

        if(!Email.endsWith(MainActivity.BASE_EMAIL_END))
        {
            Email += MainActivity.BASE_EMAIL_END;
        }
        PreviewET = (EditText) findViewById(R.id.PreviewTextField);
        PreviewET.setText(Email);
    
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int  height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9) ,(int)(height*.9) );


    }

    @Override
    public void onBackPressed(){
        MainActivity.Email = PreviewET.getText().toString();
        super.onBackPressed();

    }

}
