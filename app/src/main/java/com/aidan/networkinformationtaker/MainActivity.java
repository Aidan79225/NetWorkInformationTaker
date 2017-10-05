package com.aidan.networkinformationtaker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView getNetWorkTextView;
    private TextView getSimTextView;
    private TextView numberTextView, imeiTextView, imsiTextView, roamingStatusTextView,
            countryTextView, operatorTextView, operatorNameTextView, networkTypeTextView, phoneTypeTextView;

    private TextView internetStatusTextView, netWorkTypeTextView,netWorkDetailedStateTextView,
            netWorkStateTextView,netWorkExtraTextView,netWorkReasonTextView,netWorkSubTypeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        configView();
    }

    private void findView() {
        getSimTextView = (TextView) findViewById(R.id.getSimTextView);
        getNetWorkTextView = (TextView) findViewById(R.id.getNetWorkTextView);
        numberTextView = (TextView) findViewById(R.id.numberTextView);
        imeiTextView = (TextView) findViewById(R.id.imeiTextView);
        imsiTextView = (TextView) findViewById(R.id.imsiTextView);
        roamingStatusTextView = (TextView) findViewById(R.id.roamingStatusTextView);
        countryTextView = (TextView) findViewById(R.id.countryTextView);
        operatorTextView = (TextView) findViewById(R.id.operatorTextView);
        operatorNameTextView = (TextView) findViewById(R.id.operatorNameTextView);
        networkTypeTextView = (TextView) findViewById(R.id.networkTypeTextView);
        phoneTypeTextView = (TextView) findViewById(R.id.phoneTypeTextView);
        internetStatusTextView = (TextView) findViewById(R.id.internetStatusTextView);
        netWorkTypeTextView = (TextView) findViewById(R.id.netWorkTypeTextView);
        netWorkDetailedStateTextView = (TextView) findViewById(R.id.netWorkDetailedStateTextView);
        netWorkStateTextView= (TextView) findViewById(R.id.netWorkStateTextView);
        netWorkExtraTextView = (TextView) findViewById(R.id.netWorkExtraTextView);
        netWorkReasonTextView = (TextView) findViewById(R.id.netWorkReasonTextView);
        netWorkSubTypeTextView = (TextView) findViewById(R.id.netWorkSubTypeTextView);
    }

    private void configView() {
        getNetWorkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInternet(MainActivity.this);
            }
        });
        getSimTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSimInformation();
            }
        });
    }

    private void checkInternet(Context context) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            reloadNetWorkView(mNetworkInfo);
        } catch (Exception e) {
            internetStatusTextView.setText("爆炸");
            e.printStackTrace();
        }
    }

    private void reloadNetWorkView(NetworkInfo mNetworkInfo) {
        if (mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting()) {
            internetStatusTextView.setText("已連接");
            netWorkTypeTextView.setText(mNetworkInfo.getTypeName());
            netWorkDetailedStateTextView.setText(mNetworkInfo.getDetailedState().toString());
            netWorkStateTextView.setText(mNetworkInfo.getState().toString());
            netWorkExtraTextView.setText(mNetworkInfo.getExtraInfo());
            netWorkReasonTextView.setText(mNetworkInfo.getReason());
            netWorkSubTypeTextView.setText(mNetworkInfo.getSubtypeName());
        } else {
            internetStatusTextView.setText("未連接");
        }

    }

    private void getSimInformation() {
        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String lineNumber = telManager.getLine1Number();                                // 手機號碼
        String imei = telManager.getDeviceId();                                         // 手機 IMEI
        String imsi = telManager.getSubscriberId();                                     // 手機 IMSI
        String roamingStatus = telManager.isNetworkRoaming() ? "漫遊中" : "非漫遊";       // 手機漫遊狀態
        String country = telManager.getNetworkCountryIso();                             // 電信網路國別
        String operator = telManager.getNetworkOperator();                              // 電信公司代號
        String operatorName = telManager.getNetworkOperatorName();                      // 電信公司名稱
        String[] networkTypeArray = {"UNKNOWN", "GPRS", "EDGE", "UMTS", "CDMA",         // 行動網路類型
                "EVDO 0", "EVDO A", "1xRTT", "HSDPA", "HSUPA", "HSPA", "IDEN", "EVDO_B", "LTE", "EHRPD", "HSPAP", "GSM", "TD_SCDMA", "IWLAN", "LTE_CA"};
        String networkType = networkTypeArray[telManager.getNetworkType()];
        String[] phoneTypeArray = {"NONE", "GSM", "CDMA"};                              // 行動通訊類型
        String phoneType = phoneTypeArray[telManager.getPhoneType()];
        numberTextView.setText(lineNumber);
        imeiTextView.setText(imei);
        imsiTextView.setText(imsi);
        roamingStatusTextView.setText(roamingStatus);
        countryTextView.setText(country);
        operatorTextView.setText(operator);
        operatorNameTextView.setText(operatorName);
        networkTypeTextView.setText(networkType);
        phoneTypeTextView.setText(phoneType);
    }
}
