package sg.edu.rp.c347.ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Button btnAdd;
    ArrayAdapter aa;
    ArrayList<Task> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        btnAdd = findViewById(R.id.btnAdd);

        DBHelper db = new DBHelper(MainActivity.this);
        task = db.getAllTasks();

        aa = new TaskAdapter(MainActivity.this, R.layout.row, task);
        lv.setAdapter(aa);
        lv.refreshDrawableState();

        db.close();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,
                        SecondActivity.class);
                startActivityForResult(i, 9);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){

            DBHelper dbh = new DBHelper(MainActivity.this);
            task.clear();
            task.addAll(dbh.getAllTasks());
            dbh.close();

            aa.notifyDataSetChanged();

        }
    }

}
