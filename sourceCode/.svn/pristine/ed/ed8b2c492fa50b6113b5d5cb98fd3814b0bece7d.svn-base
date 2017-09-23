package com.sunvote.udptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    private EditText sreverIpView;
    private EditText serverPortView;
    private EditText contentView;

    private TextView webviewtest;

    private View test;

    public static String serverIp = "192.168.0.104";
    public static int port ;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        sreverIpView = (EditText) findViewById(R.id.server_ip);
        serverPortView = (EditText) findViewById(R.id.server_port);
        contentView = (EditText)findViewById(R.id.content);
        webviewtest = (TextView)findViewById(R.id.webviewtest);

        test = findViewById(R.id.test);

        webviewtest.setText(Html.fromHtml("<img alt=\"菁优网\" src=\"http://img.jyeoo.net/quiz/images/201410/118/38290219.png\" style=\"vertical-align:middle;FLOAT:right;\" />解：如图所示：∵在△ABC中，∠C=90°，有一点既在BC的对称轴上，又在AC的对称轴上，<br />∴由直角三角形斜边的中点到三角形三个顶点的距离相等，则该点一定是AB中点．<br />故选：D．"));

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverIp = sreverIpView.getText().toString();
                try{
                    port = Integer.parseInt(serverPortView.getText().toString());
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this,"端口错误",Toast.LENGTH_SHORT).show();
                    return;
                }
                content = contentView.getText().toString();
                content = content.replace(" ","");

                if(TextUtils.isEmpty(serverIp)){
                    Toast.makeText(MainActivity.this,"ip 為空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(content)){
                    Toast.makeText(MainActivity.this,"ip 輸入發送內容為空",Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(){
                    public void run(){
                        byte[] datas = hexStringToBytes(content);
                        boolean ret = false;
                        try{
                            InetAddress address = InetAddress.getByName(serverIp);
                            ret = LocalUDPDataSender.getInstance().send(datas,datas.length)>=0;
                        }catch (Exception ex){
                            ret = false;
                        }
                        final boolean result = ret;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"send:" + result,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }.start();
            }
        });
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
