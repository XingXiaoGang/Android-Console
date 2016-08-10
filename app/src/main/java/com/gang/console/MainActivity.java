package com.gang.console;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fenghuo.utils.hash.CipherUtil_O;
import com.fenghuo.utils.hash.CipherUtils;

/**
 * Created by xingxiaogang on 2016/8/10.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView mShowView;
    private EditText mInputKeyView;
    private EditText mInputContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mInputKeyView = (EditText) findViewById(R.id.input_key_view);
        mInputContentView = (EditText) findViewById(R.id.input_content_view);
        mShowView = (TextView) findViewById(R.id.text_show);

        findViewById(R.id.start_shell).setOnClickListener(this);
        findViewById(R.id.start_socket).setOnClickListener(this);
        findViewById(R.id.btn_encryp).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_shell: {
                Intent intent = new Intent(MainActivity.this, ShellActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.start_socket: {
                Intent intent = new Intent(MainActivity.this, SocketActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_encryp: {
                //todo 目前 加密的bug:CipherUtil_O 加密的结果和key没关系 ,而且不 稳定
                //todo       CipherUtils  对key的长度有要求
                String text = mInputContentView.getText().toString();
                String key = mInputKeyView.getText().toString();

                StringBuilder sb = new StringBuilder();
                byte[] desEncrypt = CipherUtils.DES_encrypt(text, key);
                String encrypored = CipherUtils.encodeBase64(desEncrypt);
                sb.append("原文:").append(text).append("\n");
                sb.append("加密后：").append(encrypored).append("\n"); // 0123 -> CCFhUnCLbdQ=
                byte[] decrypt = CipherUtils.DES_decrypt(desEncrypt, key);
                sb.append("还原：").append(decrypt != null ? new String(decrypt) : "null").append("\n");

                sb.append("\n");

                byte[] encrypredByte2 = CipherUtil_O.encrypt(text.getBytes(), key);
                String encrypored2 = CipherUtil_O.parseByte2HexStr(encrypredByte2);
                sb.append("加密后：").append(encrypored2).append("\n"); // 0123-> ACDD4D21C890B7C286FD714BCD1DD7F4
                sb.append("还原：").append(new String(CipherUtil_O.decrypt(encrypredByte2, key))).append("\n");
                mShowView.setText(sb.toString());
                break;
            }
        }
    }
}
