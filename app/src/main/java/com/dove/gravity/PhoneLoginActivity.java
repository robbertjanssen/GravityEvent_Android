package com.dove.gravity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sinch.verification.Config;
import com.sinch.verification.PhoneNumberFormattingTextWatcher;
import com.sinch.verification.PhoneNumberUtils;
import com.sinch.verification.SinchVerification;
import com.sinch.verification.Verification;
import com.sinch.verification.VerificationListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/*
 * Created by zhexun on 6/16/2016.
 */
public class PhoneLoginActivity extends AppCompatActivity {

    public static final String SMS = "sms";
    public static final String FLASHCALL = "flashcall";
    public static final String INTENT_PHONENUMBER = "phonenumber";
    public static final String INTENT_METHOD = "method";

    private EditText mPhoneNumber1;
    private TextView Country;
    private EditText mPhoneNumber2;
    private Button mSmsButton;
    private Button mFlashCallButton;
    private String mCountryIso;
    private TextWatcher mNumberTextWatcher;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone_login);
//        Button next = (Button)findViewById(R.id.btn_next);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(PhoneLoginActivity.this, SmsActivity.class);
//                startActivity(i);
//            }
//        });









        Country = (TextView) findViewById(R.id.txt1);

        mPhoneNumber2 = (EditText) findViewById(R.id.phonenumber2);
        mPhoneNumber1 = (EditText) findViewById(R.id.phonenumber1);
        mSmsButton = (Button) findViewById(R.id.btn_next);
        mSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(getE164Number(), SMS);

            }
        });
//        mFlashCallButton = (Button) findViewById(R.id.callVerificationButton);

        mCountryIso = PhoneNumberUtils.getDefaultCountryIso(this);
        final String defaultCountryName = new Locale("", mCountryIso).getDisplayName();
        final CountrySpinner spinner = (CountrySpinner) findViewById(R.id.spinner);
        spinner.init(defaultCountryName);
        spinner.addCountryIsoSelectedListener(new CountrySpinner.CountryIsoSelectedListener() {
            @Override
            public void onCountryIsoSelected(String selectedIso) {
                if (selectedIso != null) {
                    mCountryIso = selectedIso;
                    resetNumberTextWatcher(mCountryIso);
                    // force update:
                    mNumberTextWatcher.afterTextChanged(mPhoneNumber2.getText());
                    Country.setText(spinner.getSelectedItem().toString());
                }
            }
        });
        resetNumberTextWatcher(mCountryIso);

        tryAndPrefillPhoneNumber();
    }

    private void tryAndPrefillPhoneNumber() {
        if (checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            mPhoneNumber2.setText(manager.getLine1Number());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            tryAndPrefillPhoneNumber();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "This application needs permission to read your phone number to automatically "
                        + "pre-fill it", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openActivity(String phoneNumber, String method) {
        Intent verification = new Intent(this, SmsActivity.class);
        Log.e("TAG", INTENT_PHONENUMBER);
        verification.putExtra(INTENT_PHONENUMBER, phoneNumber);
        verification.putExtra(INTENT_METHOD, method);
        startActivity(verification);
    }

    private void setButtonsEnabled(boolean enabled) {
        mSmsButton.setEnabled(enabled);
//        mFlashCallButton.setEnabled(enabled);
    }

    public void onButtonClicked(View view) {
        if (view == mSmsButton) {
            openActivity(getE164Number(), SMS);
        }
// else if (view == mFlashCallButton) {
//            openActivity(getE164Number(), FLASHCALL);
//        }
    }

    private void resetNumberTextWatcher(String countryIso) {

        if (mNumberTextWatcher != null) {
            mPhoneNumber2.removeTextChangedListener(mNumberTextWatcher);
        }

        mPhoneNumber1.setText(Iso2Phone.getPhone(mCountryIso).replace("+","").replace("-",""));
        mNumberTextWatcher = new PhoneNumberFormattingTextWatcher(countryIso) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                super.beforeTextChanged(s, start, count, after);
            }

            @Override
            public synchronized void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (isPossiblePhoneNumber()) {
                    setButtonsEnabled(true);
                    mPhoneNumber2.setTextColor(Color.WHITE);
                } else {
                    setButtonsEnabled(false);
                    mPhoneNumber2.setTextColor(Color.RED);
                }
            }
        };

        mPhoneNumber2.addTextChangedListener(mNumberTextWatcher);
    }

    private boolean isPossiblePhoneNumber() {
        return PhoneNumberUtils.isPossibleNumber(mPhoneNumber2.getText().toString(), mCountryIso);
    }

    private String getE164Number() {
        return PhoneNumberUtils.formatNumberToE164(mPhoneNumber2.getText().toString(), mCountryIso);
    }

    public static class Iso2Phone {

        public static String getPhone(String code) {
            return country2phone.get(code.toUpperCase());
        }

        public static Map<String, String> getAll() {
            return country2phone;
        }

        private static Map<String, String> country2phone = new HashMap<String, String>();

        static {
            country2phone.put("AF", "+93");
            country2phone.put("AL", "+355");
            country2phone.put("DZ", "+213");
            country2phone.put("AD", "+376");
            country2phone.put("AO", "+244");
            country2phone.put("AG", "+1-268");
            country2phone.put("AR", "+54");
            country2phone.put("AM", "+374");
            country2phone.put("AU", "+61");
            country2phone.put("AT", "+43");
            country2phone.put("AZ", "+994");
            country2phone.put("BS", "+1-242");
            country2phone.put("BH", "+973");
            country2phone.put("BD", "+880");
            country2phone.put("BB", "+1-246");
            country2phone.put("BY", "+375");
            country2phone.put("BE", "+32");
            country2phone.put("BZ", "+501");
            country2phone.put("BJ", "+229");
            country2phone.put("BT", "+975");
            country2phone.put("BO", "+591");
            country2phone.put("BA", "+387");
            country2phone.put("BW", "+267");
            country2phone.put("BR", "+55");
            country2phone.put("BN", "+673");
            country2phone.put("BG", "+359");
            country2phone.put("BF", "+226");
            country2phone.put("BI", "+257");
            country2phone.put("KH", "+855");
            country2phone.put("CM", "+237");
            country2phone.put("CA", "+1");
            country2phone.put("CV", "+238");
            country2phone.put("CF", "+236");
            country2phone.put("TD", "+235");
            country2phone.put("CL", "+56");
            country2phone.put("CN", "+86");
            country2phone.put("CO", "+57");
            country2phone.put("KM", "+269");
            country2phone.put("CD", "+243");
            country2phone.put("CG", "+242");
            country2phone.put("CR", "+506");
            country2phone.put("CI", "+225");
            country2phone.put("HR", "+385");
            country2phone.put("CU", "+53");
            country2phone.put("CY", "+357");
            country2phone.put("CZ", "+420");
            country2phone.put("DK", "+45");
            country2phone.put("DJ", "+253");
            country2phone.put("DM", "+1-767");
            country2phone.put("DO", "+1-809and1-829");
            country2phone.put("EC", "+593");
            country2phone.put("EG", "+20");
            country2phone.put("SV", "+503");
            country2phone.put("GQ", "+240");
            country2phone.put("ER", "+291");
            country2phone.put("EE", "+372");
            country2phone.put("ET", "+251");
            country2phone.put("FJ", "+679");
            country2phone.put("FI", "+358");
            country2phone.put("FR", "+33");
            country2phone.put("GA", "+241");
            country2phone.put("GM", "+220");
            country2phone.put("GE", "+995");
            country2phone.put("DE", "+49");
            country2phone.put("GH", "+233");
            country2phone.put("GR", "+30");
            country2phone.put("GD", "+1-473");
            country2phone.put("GT", "+502");
            country2phone.put("GN", "+224");
            country2phone.put("GW", "+245");
            country2phone.put("GY", "+592");
            country2phone.put("HT", "+509");
            country2phone.put("HN", "+504");
            country2phone.put("HU", "+36");
            country2phone.put("IS", "+354");
            country2phone.put("IN", "+91");
            country2phone.put("ID", "+62");
            country2phone.put("IR", "+98");
            country2phone.put("IQ", "+964");
            country2phone.put("IE", "+353");
            country2phone.put("IL", "+972");
            country2phone.put("IT", "+39");
            country2phone.put("JM", "+1-876");
            country2phone.put("JP", "+81");
            country2phone.put("JO", "+962");
            country2phone.put("KZ", "+7");
            country2phone.put("KE", "+254");
            country2phone.put("KI", "+686");
            country2phone.put("KP", "+850");
            country2phone.put("KR", "+82");
            country2phone.put("KW", "+965");
            country2phone.put("KG", "+996");
            country2phone.put("LA", "+856");
            country2phone.put("LV", "+371");
            country2phone.put("LB", "+961");
            country2phone.put("LS", "+266");
            country2phone.put("LR", "+231");
            country2phone.put("LY", "+218");
            country2phone.put("LI", "+423");
            country2phone.put("LT", "+370");
            country2phone.put("LU", "+352");
            country2phone.put("MK", "+389");
            country2phone.put("MG", "+261");
            country2phone.put("MW", "+265");
            country2phone.put("MY", "+60");
            country2phone.put("MV", "+960");
            country2phone.put("ML", "+223");
            country2phone.put("MT", "+356");
            country2phone.put("MH", "+692");
            country2phone.put("MR", "+222");
            country2phone.put("MU", "+230");
            country2phone.put("MX", "+52");
            country2phone.put("FM", "+691");
            country2phone.put("MD", "+373");
            country2phone.put("MC", "+377");
            country2phone.put("MN", "+976");
            country2phone.put("ME", "+382");
            country2phone.put("MA", "+212");
            country2phone.put("MZ", "+258");
            country2phone.put("MM", "+95");
            country2phone.put("NA", "+264");
            country2phone.put("NR", "+674");
            country2phone.put("NP", "+977");
            country2phone.put("NL", "+31");
            country2phone.put("NZ", "+64");
            country2phone.put("NI", "+505");
            country2phone.put("NE", "+227");
            country2phone.put("NG", "+234");
            country2phone.put("NO", "+47");
            country2phone.put("OM", "+968");
            country2phone.put("PK", "+92");
            country2phone.put("PW", "+680");
            country2phone.put("PA", "+507");
            country2phone.put("PG", "+675");
            country2phone.put("PY", "+595");
            country2phone.put("PE", "+51");
            country2phone.put("PH", "+63");
            country2phone.put("PL", "+48");
            country2phone.put("PT", "+351");
            country2phone.put("QA", "+974");
            country2phone.put("RO", "+40");
            country2phone.put("RU", "+7");
            country2phone.put("RW", "+250");
            country2phone.put("KN", "+1-869");
            country2phone.put("LC", "+1-758");
            country2phone.put("VC", "+1-784");
            country2phone.put("WS", "+685");
            country2phone.put("SM", "+378");
            country2phone.put("ST", "+239");
            country2phone.put("SA", "+966");
            country2phone.put("SN", "+221");
            country2phone.put("RS", "+381");
            country2phone.put("SC", "+248");
            country2phone.put("SL", "+232");
            country2phone.put("SG", "+65");
            country2phone.put("SK", "+421");
            country2phone.put("SI", "+386");
            country2phone.put("SB", "+677");
            country2phone.put("SO", "+252");
            country2phone.put("ZA", "+27");
            country2phone.put("ES", "+34");
            country2phone.put("LK", "+94");
            country2phone.put("SD", "+249");
            country2phone.put("SR", "+597");
            country2phone.put("SZ", "+268");
            country2phone.put("SE", "+46");
            country2phone.put("CH", "+41");
            country2phone.put("SY", "+963");
            country2phone.put("TJ", "+992");
            country2phone.put("TZ", "+255");
            country2phone.put("TH", "+66");
            country2phone.put("TL", "+670");
            country2phone.put("TG", "+228");
            country2phone.put("TO", "+676");
            country2phone.put("TT", "+1-868");
            country2phone.put("TN", "+216");
            country2phone.put("TR", "+90");
            country2phone.put("TM", "+993");
            country2phone.put("TV", "+688");
            country2phone.put("UG", "+256");
            country2phone.put("UA", "+380");
            country2phone.put("AE", "+971");
            country2phone.put("GB", "+44");
            country2phone.put("US", "+1");
            country2phone.put("UY", "+598");
            country2phone.put("UZ", "+998");
            country2phone.put("VU", "+678");
            country2phone.put("VA", "+379");
            country2phone.put("VE", "+58");
            country2phone.put("VN", "+84");
            country2phone.put("YE", "+967");
            country2phone.put("ZM", "+260");
            country2phone.put("ZW", "+263");
            country2phone.put("GE", "+995");
            country2phone.put("TW", "+886");
            country2phone.put("AZ", "+374-97");
            country2phone.put("CY", "+90-392");
            country2phone.put("MD", "+373-533");
            country2phone.put("SO", "+252");
            country2phone.put("GE", "+995");
            country2phone.put("CX", "+61");
            country2phone.put("CC", "+61");
            country2phone.put("NF", "+672");
            country2phone.put("NC", "+687");
            country2phone.put("PF", "+689");
            country2phone.put("YT", "+262");
            country2phone.put("GP", "+590");
            country2phone.put("GP", "+590");
            country2phone.put("PM", "+508");
            country2phone.put("WF", "+681");
            country2phone.put("CK", "+682");
            country2phone.put("NU", "+683");
            country2phone.put("TK", "+690");
            country2phone.put("GG", "+44");
            country2phone.put("IM", "+44");
            country2phone.put("JE", "+44");
            country2phone.put("AI", "+1-264");
            country2phone.put("BM", "+1-441");
            country2phone.put("IO", "+246");
            country2phone.put("", "+357");
            country2phone.put("VG", "+1-284");
            country2phone.put("KY", "+1-345");
            country2phone.put("FK", "+500");
            country2phone.put("GI", "+350");
            country2phone.put("MS", "+1-664");
            country2phone.put("SH", "+290");
            country2phone.put("TC", "+1-649");
            country2phone.put("MP", "+1-670");
            country2phone.put("PR", "+1-787and1-939");
            country2phone.put("AS", "+1-684");
            country2phone.put("GU", "+1-671");
            country2phone.put("VI", "+1-340");
            country2phone.put("HK", "+852");
            country2phone.put("MO", "+853");
            country2phone.put("FO", "+298");
            country2phone.put("GL", "+299");
            country2phone.put("GF", "+594");
            country2phone.put("GP", "+590");
            country2phone.put("MQ", "+596");
            country2phone.put("RE", "+262");
            country2phone.put("AX", "+358-18");
            country2phone.put("AW", "+297");
            country2phone.put("AN", "+599");
            country2phone.put("SJ", "+47");
            country2phone.put("AC", "+247");
            country2phone.put("TA", "+290");
            country2phone.put("CS", "+381");
            country2phone.put("PS", "+970");
            country2phone.put("EH", "+212");
        }
    }
}
