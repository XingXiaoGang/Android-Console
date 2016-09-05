package com.gang.console;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gang.soket.MinaClient;
import com.gang.soket.MinaServer;
import com.gang.soket.utils.NetUtils;

/**
 * Created by xingxiaogang on 2016/8/10.
 */
public class SocketActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PORT = 10002;
    MinaClient minaClient;
    private TextView ipTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_socket);
        ipTextView = (TextView) findViewById(R.id.ip_text);

        findViewById(R.id.start_server).setOnClickListener(this);
        findViewById(R.id.close_server).setOnClickListener(this);
        findViewById(R.id.send_msg_to_server).setOnClickListener(this);
        findViewById(R.id.connect_to_server).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ipTextView.setText("请访问http://" + NetUtils.getIP4Adress(getApplicationContext()) + ":" + PORT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_server: {
                MinaServer.getInstance().start(PORT);
                break;
            }
            case R.id.close_server: {
                MinaServer.getInstance().stop();
                break;
            }
            case R.id.connect_to_server: {
                minaClient = new MinaClient();
                minaClient.start(NetUtils.getIP4Adress(getApplicationContext()), PORT);
                break;
            }
            case R.id.send_msg_to_server: {
                minaClient.sendMessage("你好呀~");
                break;
            }
        }
    }
}
