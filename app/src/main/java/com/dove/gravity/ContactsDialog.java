package com.dove.gravity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by zhexun on 6/16/2016.
 */
public class ContactsDialog extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button confirm;

    public ContactsDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_contacts);
        confirm = (Button) findViewById(R.id.btn_ok);
        confirm.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:

                break;
            case R.id.btn_dontallow:
                break;
            default:
                break;
        }
        Intent i = new Intent(c,PhoneLoginActivity.class);
        c.startActivity(i);
        dismiss();
    }
}