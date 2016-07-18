package com.dove.gravity;

import android.app.Activity;
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
public class ContactsLocationDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button confirm;

    public ContactsLocationDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_contacts_location);
        confirm = (Button) findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:

                LocationDialog cdd=new LocationDialog(c);

                cdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
                WindowManager.LayoutParams wmlp = cdd.getWindow().getAttributes();
                wmlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;

                wmlp.y = 150;   //y position
                cdd.setCanceledOnTouchOutside(false);
                cdd.show();
                break;
            default:
                break;
        }
        dismiss();
    }
}