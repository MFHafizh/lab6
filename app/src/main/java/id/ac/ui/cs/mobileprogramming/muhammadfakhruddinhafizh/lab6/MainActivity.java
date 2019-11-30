package id.ac.ui.cs.mobileprogramming.muhammadfakhruddinhafizh.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String WIFI_SSID = "Hafizh";
    private final String WIFI_KEY = "12345678";
    private EditText input_1, input_2;
    private TextView result;
    private Button calculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNewWifiConfig();
        connectToSpesificSSID();

        input_1 = findViewById(R.id.input_1);
        input_2 = findViewById(R.id.input_2);
        result = findViewById(R.id.result);
        calculate = findViewById(R.id.calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_1.getText().toString().equals("")) {
                    input_1.requestFocus();
                    input_1.setError("Fill this field");
                }
                if (input_2.getText().toString().equals("")) {
                    input_2.requestFocus();
                    input_2.setError("Fill this field");
                }
                String num_1 = input_1.getText().toString();
                String num_2 = input_2.getText().toString();

                if (!num_1.equals("") && !num_2.equals("")) {
                    result.setText("Result from JNI: " +
                            multiplyJNI(Integer.parseInt(num_1), Integer.parseInt(num_2)));
                }
            }
        });
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

    public native int multiplyJNI(int numberA, int numberB);

    static {
        System.loadLibrary("multiply");
    }
}
