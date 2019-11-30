package id.ac.ui.cs.mobileprogramming.muhammadfakhruddinhafizh.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String WIFI_SSID = "Hafizh";
    private final String WIFI_KEY = "12345678";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNewWifiConfig();
        connectToSpesificSSID();
    }

    @Override
    protected void onResume() {
        connectToSpesificSSID();
        super.onResume();
    }

    protected void connectToSpesificSSID() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            List<WifiConfiguration> wifi_list = wifiManager.getConfiguredNetworks();
            for( WifiConfiguration i : wifi_list ) {
                if(i.SSID != null && i.SSID.equals("\"" + WIFI_SSID + "\"")) {
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(i.networkId, true);
                    wifiManager.reconnect();

                    break;
                }
            }

        }
    }

    protected void addNewWifiConfig() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"" + WIFI_SSID + "\"");
        wifiConfig.preSharedKey = String.format("\"" + WIFI_KEY + "\"");
        wifiManager.addNetwork(wifiConfig);
    }
}
