package edu.coe.asmarek.mybookshelf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LauncherActivity extends AppCompatActivity {

    final private OpenHelper db = new OpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        SharedPreferences s = getSharedPreferences("myFile", 0);
        SharedPreferences.Editor e = s.edit();

        boolean newUser = s.getBoolean("newUser", true);
        if(newUser) {
            db.addShelf("MyShelf");
            db.addShelf("Wishlist");
            db.addShelf("Loans");

            db.close();

            e.putBoolean("newUser", false);
        }

        Intent i = new Intent("edu.coe.asmarek.mybookshelf.BookListViewActivity");
        i.putExtra("shelfName", "MyShelf");
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
