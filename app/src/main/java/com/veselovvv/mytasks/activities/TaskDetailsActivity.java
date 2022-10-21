package com.veselovvv.mytasks.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.veselovvv.mytasks.App;
import com.veselovvv.mytasks.R;
import com.veselovvv.mytasks.models.Task;

public class TaskDetailsActivity extends AppCompatActivity {
    private static final String EXTRA_TASK = "TaskDetailsActivity.EXTRA_TASK";
    private Task task;
    private EditText editText;

    public static void start(Activity caller, Task task) {
        Intent intent = new Intent(caller, TaskDetailsActivity.class);

        if (task != null) intent.putExtra(EXTRA_TASK, task);
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.task_details_title));
        editText = findViewById(R.id.text);

        if (getIntent().hasExtra(EXTRA_TASK)) {
            task = getIntent().getParcelableExtra(EXTRA_TASK);
            if (task != null) editText.setText(task.text); else editText.setText(R.string.no_text);
        } else
            task = new Task();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                if (editText.getText().length() > 0) {
                    task.text = editText.getText().toString();
                    task.done = false;
                    task.timestamp = System.currentTimeMillis();
                    
                    if (getIntent().hasExtra(EXTRA_TASK)) App.getInstance().getTaskDao().update(task);
                    else App.getInstance().getTaskDao().insert(task);
                    
                    finish();
                }
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
