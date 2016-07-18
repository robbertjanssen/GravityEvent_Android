package com.dove.gravity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class AllowAccessActivity extends AppCompatActivity {


    private boolean isPermissionAsked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_access);

        Button next = (Button)findViewById(R.id.goToNextScreen);
        if (next != null) {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isPermissionAsked) {
                        Intent i = new Intent(AllowAccessActivity.this, PhoneLoginActivity.class);
                        startActivity(i);
                    }
                    else{
                        LocationDialog cdd=new LocationDialog(AllowAccessActivity.this);

                        cdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        WindowManager.LayoutParams wmlp = cdd.getWindow().getAttributes();

                        wmlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                        wmlp.y = 150;   //y position

                        Window window = cdd.getWindow();
                        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        cdd.show();

                        isPermissionAsked= true;
                    }

                }
            });
        }
    }

}
