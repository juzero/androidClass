package com.example.donggukalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    TextView [] keyword = new TextView[10];
    EditText insertKeyWord;
    LinearLayout layout;
    Button btnDelete;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    int toggle;
    int count;
    int listcount;

    // 리스트뷰 추가
    LinkedList<String> keywordList;
    ArrayAdapter<String> mAdapter;
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        delete();
    }

    //단어 추가
    public void addKeyWord(View v){
        int a = 0;
        String getKeyword = insertKeyWord.getText().toString();
        getKeyword = getKeyword.trim();
        if(getKeyword.getBytes().length <= 0){
            Toast.makeText(getApplicationContext(), "단어를 입력해주세요", Toast.LENGTH_SHORT).show();
        } else {
            if (count == 0) {
                keyword[count].setText(getKeyword);
                keywordList.add(keyword[count].getText().toString());
                mAdapter.notifyDataSetChanged();
                editor.putString("keyword" + count, getKeyword);
                editor.commit();
                count++;
                insertKeyWord.setText("");
            } else if (count > 0 && count <= 4) {
                keyword[count].setText(getKeyword);
                for (int i = 0; i < count; i++) {
                    if (keyword[i].getText().toString().equals(keyword[count].getText().toString())) {
                        Toast.makeText(getApplicationContext(), "이미 추가하신 관심사입니다", Toast.LENGTH_SHORT).show();
                        insertKeyWord.setText("");
                        a = 1;
                    }
                }
                if (a == 0) {
                    keywordList.add( keyword[count].getText().toString());
                    mAdapter.notifyDataSetChanged();
                    editor.putString("keyword" + count, getKeyword);
                    editor.commit();
                    insertKeyWord.setText("");
                    count++;
                }
            } else if (count > 4) {
                keyword[count].setText("");
                Toast.makeText(getApplicationContext(), "최대 5개까지 등록 가능합니다.", Toast.LENGTH_SHORT).show();
                insertKeyWord.setText("");
            }
        }
    }

    //단어 삭제
    public void deleteKeyword(View v) {
        listcount = mAdapter.getCount();
        if (listcount > 0) {
            if (toggle == 0) { //삭제를 하려는 경우
                toggle = 1;
                btnDelete.setText("완료");
                Toast.makeText(getApplicationContext(), "삭제할 단어를 누르고 완료버튼을 눌러주세요!", Toast.LENGTH_LONG).show();
            } else if(toggle ==1 ) { // 삭제 완료한 경우
                toggle = 0;
                Toast.makeText(getApplicationContext(), "삭제가 완료되었습니다", Toast.LENGTH_LONG).show();
                btnDelete.setText("삭제");
            }
        } else if(listcount <=0){
            if(toggle== 1) { //전부 삭제한 경우
                Toast.makeText(getApplicationContext(), "삭제가 완료되었습니다", Toast.LENGTH_LONG).show();
                btnDelete.setText("삭제");
            } else { // 애초에 단어가 없는경우
                Toast.makeText(getApplicationContext(), "삭제할 단어가 없습니다.", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void delete() {
        listcount = mAdapter.getCount();
        if(toggle == 0){
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (count > 4) {
                            count--;
                            for (int i = position; i < count-1; i++) {
                                //keyword[i] = keyword[i+1];
                                editor.putString("keyword" + i, keyword[i + 1].getText().toString());
                                editor.commit();
                            }
                            keywordList.remove(position);
                            mAdapter.notifyDataSetChanged();
                        }else if (count > 0 && count <= 4) {
                            for (int i = position; i < count; i++) {
                                //keyword[i] = keyword[i+1];
                                editor.putString("keyword" + i, keyword[i + 1].getText().toString());
                                editor.commit();
                            }
                            count--;
                            keywordList.remove(position);
                            mAdapter.notifyDataSetChanged();
                        }else if(count == 0 ){
                            for (int i = position; i < count; i++) {
                                //keyword[i] = keyword[i+1];
                                editor.putString("keyword" + i, keyword[i + 1].getText().toString());
                                editor.commit();
                            }
                            keywordList.remove(position);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
        }
    }

    public void show(View v){

        for(int i =0 ; i < count; i++){
            keywordList.add(settings.getString("keyword"+i,""));
            mAdapter.notifyDataSetChanged();
        }
    }

    //변수 선언
    public void init(){
        insertKeyWord = (EditText)findViewById(R.id.insertKeyword);
        keyword = new TextView[6];
        btnDelete = (Button)findViewById(R.id.deleteButton);


        for(int i =0 ; i <6; i ++){
            keyword[i] = new TextView(this);
            keyword[i].setTextSize(30);
            keyword[i].setTextColor(Color.BLACK);
        }
        count = 0;
        toggle = 0;
        settings = getSharedPreferences("Keyword", 0);
        editor = settings.edit();

        //listview
        mListView = (ListView)findViewById(R.id.listView);
        keywordList = new LinkedList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,keywordList);
        mListView.setAdapter(mAdapter);

    }

}
