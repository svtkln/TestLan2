package com.example.svtkln.ex1_button_yesno;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    Button btnToogle;
    EditText edtInputStr;
    TextView tvDataFromFile;


    boolean isButtonShowWrite = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtInputStr = (EditText) findViewById(R.id.edtInputStr);
        tvDataFromFile = (TextView) findViewById(R.id.tvDataFromFile);
        btnToogle = (Button) findViewById(R.id.btnToogle);
        btnToogle.setOnClickListener(onClick_btnToogle);
    }

    /**
     * isButtonShowWrite = false -> ghi vào file
     *
     * @param isButtonShowWrite
     */
    private void writeOrRead(boolean isButtonShowWrite) {
        if (isButtonShowWrite) {
            //đọc từ file
            String strDataFromFile = readFromFile();
            tvDataFromFile.setText(strDataFromFile);
            edtInputStr.setEnabled(true);
        } else {
            //ghi vào file
            String strDataInput = edtInputStr.getText() + "";

            writeToFile(strDataInput);

            edtInputStr.setEnabled(false);
            tvDataFromFile.setText("");
            Toast.makeText(MainActivity.this, "WRITE SUCCESS", Toast.LENGTH_SHORT).show();
            btnToogle.setText("Read From File");
        }
    }

    /**
     * kiểm tra edtInputStr có rỗng ko
     *
     * @return
     */
    private boolean validate() {
        boolean check = false;
        if (TextUtils.isEmpty(edtInputStr.getText() + "")) {
            return true;
        }
        return check;
    }

    private View.OnClickListener onClick_btnToogle = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validate()) {
                edtInputStr.setError("Chưa nhập text");
            } else {
                if (isButtonShowWrite) {
                    isButtonShowWrite = false;
                    btnToogle.setText("Read From File");
                } else {
                    isButtonShowWrite = true;
                    btnToogle.setText("Write To File");
                }
                writeOrRead(isButtonShowWrite);
            }
        }
    };

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("test.txt", this.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile() {
        String ret = "";
        try {
            InputStream inputStream = openFileInput("test.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }
}
