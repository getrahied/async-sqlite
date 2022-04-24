package com.example.devsufy;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class MainActivity extends AppCompatActivity{
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=this.openOrCreateDatabase("Stud",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS stud (ID int, Name VARCHAR(40))");
    }
    public void insert(View view){
//Since AsyncTask is deprecated since API 30, I am using Executer Service
        ExecutorService service= Executors.newSingleThreadExecutor();
        EditText id=(EditText)findViewById(R.id.id);
        EditText name=(EditText)findViewById(R.id.name);
        String[]
                collections={id.getText().toString(),name.getText().toString()};
        Toast.makeText(this,"Values Inserted",Toast.LENGTH_LONG).show();
        service.execute(new Runnable() {
            @Override
            public void run() {
//background tasks
                database.execSQL("INSERT INTO STUD (ID, Name) VALUES (?,?)",collections);
//postExecute
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Values inserted",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public void display(View view){
        String data="";
        Cursor c=database.rawQuery("SELECT * FROM stud",null);
        int idIndex=c.getColumnIndex("ID");
        int nameIndex=c.getColumnIndex("Name");
        c.moveToFirst();
        while(!c.isAfterLast()){
            data=data+c.getString(idIndex)+" "+c.getString(nameIndex)+"\n";
            c.moveToNext();
        }
        Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
    }
}
