package edu.coe.asmarek.mybookshelf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Intent i = new Intent("edu.coe.asmarek.mybookshelf.Shelf");
        startActivity(i);
    }

    /*@Override
    protected void onRestart() {
        super.onResume();
        setContentView(R.layout.activity_launcher);

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }*/
}
