package com.example.donggukalert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView [] keyword;
    EditText insertKeyWord;
    LinearLayout layout;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void addKeyWord(View v){
        String getKeyword = insertKeyWord.getText().toString();
        getKeyword = getKeyword.trim();
        if(getKeyword.getBytes().length <= 0){
            Toast.makeText(getApplicationContext(), "단어를 입력해주세요", Toast.LENGTH_SHORT).show();
        } else {
            keyword[count] = new TextView(this);
            keyword[count].setText(getKeyword);
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    if (keyword[i].getText().toString().equals((keyword[count].getText().toString()))) {
                        Toast.makeText(getApplicationContext(), "이미 추가하신 관심사입니다", Toast.LENGTH_SHORT).show();
                    } else {
                        layout.addView(keyword[count]);
                        count++;
                    }
                }
            } else {
                layout.addView(keyword[count]);
                count++;
            }
        }
    }

    public void init(){
        insertKeyWord = (EditText)findViewById(R.id.insertKeyword);
        keyword = new TextView[10];
        layout = (LinearLayout)findViewById(R.id.LayoutKeyword);
    }
}
