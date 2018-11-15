package com.example.mysmarthome;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.service.carrier.CarrierMessagingService;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.List;

public class WifiScanner extends Thread{

    static interface ResultCallback{
        void reportScanResult(List<ScanResult> list);
    }

    private List<ScanResult> scanResults;
    private WifiManager wifiManager;
    private ResultCallback r;
    private Activity activity;
    private ListView listView;
    private final BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) && r != null){
//                scanResults = wifiManager.getScanResults();
                r.reportScanResult(wifiManager.getScanResults());
            }
        }
    };

    public WifiScanner(Activity a){
        wifiManager = (WifiManager)activity.getSystemService(Context.WIFI_SERVICE);
        activity = a;
        activity.registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public void scan(ListView list) {
        listView = list;
        r = new ResultCallback() {
            @Override
            public void reportScanResult(List<ScanResult> list) {
                ArrayAdapter<ScanResult> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
            }
        };
        wifiManager.setWifiEnabled(true);
        wifiManager.startScan();
//        if(enable) {
//            wifiManager.startScan();
//        }
//        else{
//            scanResults.clear();
//            ArrayAdapter<ScanResult> adapter = new ArrayAdapter<ScanResult>(this, android.R.layout.simple_list_item_1, scanResults);
//            listView.setAdapter(adapter);
//        }
    }
}