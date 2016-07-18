package com.dove.gravity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by zhexun on 6/16/2016.
 */
public class LocationDialog extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button confirm;

    public LocationDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_location);
        confirm = (Button) findViewById(R.id.btn_ok);
        confirm.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                break;
            case R.id.btn_dontallow:

            default:
                break;
        }
        ContactsDialog cdd=new ContactsDialog(c);

        cdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = cdd.getWindow().getAttributes();

        wmlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        wmlp.y = 150;   //y position

        Window window = cdd.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        cdd.show();
        dismiss();
    }
}