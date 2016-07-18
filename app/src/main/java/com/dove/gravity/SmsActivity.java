package com.dove.gravity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sinch.verification.CodeInterceptionException;
import com.sinch.verification.Config;
import com.sinch.verification.SinchVerification;
import com.sinch.verification.Verification;
import com.sinch.verification.VerificationListener;


import org.json.JSONObject;

import java.io.InputStream;
import java.io.StringWriter;

/*
 * Created by zhexun on 6/16/2016.
 */
public class SmsActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback  {

    private static final String TAG = Verification.class.getSimpleName();
    private String APPLICATION_KEY = "abd05436-39b3-4c83-9f5d-f80965667be9";
    private Verification mVerification;

    public String m_phoneNumber="";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sms);
        ////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////
        showProgress();
        initiateVerification();
        Button btn_resendSMS = (Button)findViewById(R.id.btn_resendSMS);
        btn_resendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showCompleted();
            }
        });
    }

    void createVerification(String phoneNumber, String method, boolean skipPermissionCheck) {
        Config config = SinchVerification.config().applicationKey(APPLICATION_KEY).context(getApplicationContext())
                .build();
        VerificationListener listener = new MyVerificationListener();

        if (method.equalsIgnoreCase(PhoneLoginActivity.SMS)) {

            if (!skipPermissionCheck && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) ==
                    PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 0);
                hideProgressBar();
            } else {
                mVerification = SinchVerification.createSmsVerification(config, phoneNumber, listener);
                mVerification.initiate();
            }

        } else {
//            TextView messageText = (TextView) findViewById(R.id.textView);
//            messageText.setText(R.string.flashcalling);
            mVerification = SinchVerification.createFlashCallVerification(config, phoneNumber, listener);
            mVerification.initiate();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "This application needs permission to read your SMS to automatically verify your "
                        + "phone, you may disable the permission once you have been verified.", Toast.LENGTH_LONG)
                        .show();
            }
            enableInputField(true);
        }
        initiateVerificationAndSuppressPermissionCheck();
    }

    void initiateVerification() {
        initiateVerification(false);
    }

    void initiateVerificationAndSuppressPermissionCheck() {
        initiateVerification(true);
    }

    void initiateVerification(boolean skipPermissionCheck) {
        Intent intent = getIntent();
        if (intent != null) {
            String phoneNumber = intent.getStringExtra(PhoneLoginActivity.INTENT_PHONENUMBER);
            String method = intent.getStringExtra(PhoneLoginActivity.INTENT_METHOD);
            TextView phoneText = (TextView) findViewById(R.id.numberText);
            phoneText.setText("We sent an SMS to "+phoneNumber);
            m_phoneNumber = phoneNumber;
            createVerification(phoneNumber, method, skipPermissionCheck);
        }
    }

    public void onSubmitClicked(View view) {
        String code = ((EditText) findViewById(R.id.inputCode)).getText().toString();
        if (!code.isEmpty()) {
            if (mVerification != null) {
                mVerification.verify(code);
                showProgress();
//                TextView messageText = (TextView) findViewById(R.id.textView);
//                messageText.setText("Verification in progress");
                enableInputField(false);
            }
        }
    }

    void enableInputField(boolean enable) {
//        View container = findViewById(R.id.inputContainer);
//        if (enable) {
//            container.setVisibility(View.VISIBLE);
//            EditText input = (EditText) findViewById(R.id.inputCode);
//            input.requestFocus();
//        } else {
//            container.setVisibility(View.GONE);
//        }
    }

    void hideProgressBarAndShowMessage(int message) {
        hideProgressBar();
//        TextView messageText = (TextView) findViewById(R.id.textView);
//        messageText.setText(message);
    }

    void hideProgressBar() {
//        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
//        progressBar.setVisibility(View.INVISIBLE);
//        TextView progressText = (TextView) findViewById(R.id.progressText);
//        progressText.setVisibility(View.INVISIBLE);
    }

    void showProgress() {
//        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
//        progressBar.setVisibility(View.VISIBLE);
    }

    void showCompleted() {
//        ImageView checkMark = (ImageView) findViewById(R.id.checkmarkImage);
//        checkMark.setVisibility(View.VISIBLE);
        Intent i = new Intent(this, FriendsActivity.class);
        startActivity(i);
    }

    class MyVerificationListener implements VerificationListener {

        @Override
        public void onInitiated() {
            Log.d(TAG, "Initialized!");
            showProgress();
        }

        @Override
        public void onInitiationFailed(Exception exception) {
            Log.e(TAG, "Verification initialization failed: " + exception.getMessage());
//            hideProgressBarAndShowMessage(R.string.failed);
        }

        @Override
        public void onVerified() {
//            Log.d(TAG, "Verified!");
//            hideProgressBarAndShowMessage(R.string.verified);
            showCompleted();
        }

        @Override
        public void onVerificationFailed(Exception exception) {
            Log.e(TAG, "Verification failed: " + exception.getMessage());
            if (exception instanceof CodeInterceptionException) {
                hideProgressBar();
            } else {
//                hideProgressBarAndShowMessage(R.string.failed);
            }
            enableInputField(true);
        }
    }


}
