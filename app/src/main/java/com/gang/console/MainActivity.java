package com.gang.console;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fenghuo.utils.shell.ShellUtils2;

/**
 * Created by xingxiaogang on 2016/5/27.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private EditText editText;
    private TextView textView;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case R.id.shell_complete: {
                    textView.append("\n" + String.valueOf(msg.obj));
                    break;
                }
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shell);
        editText = (EditText) findViewById(R.id.shell_text);
        textView = (TextView) findViewById(R.id.res_text);
        findViewById(R.id.exec_single_button).setOnClickListener(this);
        findViewById(R.id.exec_multi_button).setOnClickListener(this);
        findViewById(R.id.exec_single_root_button).setOnClickListener(this);
        findViewById(R.id.exec_multi_root_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.exec_multi_button: {
                execShellMulti(false);
                break;
            }
            case R.id.exec_multi_root_button: {
                execShellMulti(true);
                break;
            }
            case R.id.exec_single_button: {
                execShellSingle(false);
                break;
            }
            case R.id.exec_single_root_button: {
                execShellSingle(true);
                break;
            }
        }
    }

    //用同一个process执行多个
    private void execShellMulti(boolean su) {
        String shell = editText.getText().toString();
        if (!TextUtils.isEmpty(shell)) {
            String command[] = shell.split(";");
            textView.setText(shell);
            new ShellUtils2.ShellTask(mHandler, command, su).start();
        }
    }

    //一个process执行一个命令
    private void execShellSingle(boolean su) {
        String shell = editText.getText().toString();
        if (!TextUtils.isEmpty(shell)) {
            String command[] = shell.split(":");
            String path = null;
            String str = null;
            if (command.length == 2) {
                path = command[0];
                str = command[1];
            }
            if (command.length == 1) {
                str = command[0];
            }
            textView.setText(shell);
            new ShellUtils2.ShellTask(mHandler, path, str, su).start();
        }
    }

}
