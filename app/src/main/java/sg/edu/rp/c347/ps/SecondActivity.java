package sg.edu.rp.c347.ps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    int reqCode = 12345;
    Button btnAdd, btnCancel;
    EditText etName, etDesc, etTimer;

    String name, desc;
    int timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnAdd = findViewById(R.id.btnAddTask);
        btnCancel = findViewById(R.id.btnCancel);
        etName = findViewById(R.id.etname);
        etDesc = findViewById(R.id.etDesc);
        etTimer = findViewById(R.id.etTime);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = etName.getText().toString();
                desc = etDesc.getText().toString();
                timer = Integer.parseInt(etTimer.getText().toString());

                DBHelper db = new DBHelper(SecondActivity.this);
                db.insertTask(name, desc);
                db.close();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, timer);

                Intent intent = new Intent(SecondActivity.this,
                        ScheduleNotificationReceiver.class);

                Intent in = new Intent("my.action.string");
                intent.putExtra("name", name);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        SecondActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);

                setResult(RESULT_OK);
                finish();

            }
        });
    }
}
