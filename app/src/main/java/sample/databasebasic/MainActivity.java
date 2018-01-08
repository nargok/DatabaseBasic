package sample.databasebasic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SimpleDatabaseHelper helper = null;
    private EditText txtIsbn = null;
    private EditText txtTitle = null;
    private EditText txtPrice = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ヘルパーを準備
        helper = new SimpleDatabaseHelper(this);

        // データベースを取得
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            Toast.makeText(this, "接続しました", Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

}
