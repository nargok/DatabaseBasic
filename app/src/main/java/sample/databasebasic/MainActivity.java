package sample.databasebasic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        txtIsbn = (EditText) findViewById(R.id.txtIsbn);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
    }

    // 保存ボタンを押したときに呼び出されるコード
    public void onSave(View viw) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            // 登録内容の設定
            ContentValues cv = new ContentValues();
            cv.put("isbn", txtIsbn.getText().toString());
            cv.put("title", txtTitle.getText().toString());
            cv.put("price", txtPrice.getText().toString());

            // 同一データへの更新処理の指示
            db.insertWithOnConflict("books",           // 対象テーブル
                    null,                              // null列の処理方法
                    cv,                                // 登録する値
                    SQLiteDatabase.CONFLICT_REPLACE);  // データが重複したときの処理 : 置き換えを指定

            // データ登録・更新
            db.insert("books", null, cv);
            Toast.makeText(this, "データの登録に成功しました", Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

    // 削除ボタンを押したときに呼び出されるコード
    public void onDelete(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String[] params = {txtIsbn.getText().toString()};
            db.delete("books", "isbn = ?", params);
            Toast.makeText(this, "データの削除に成功しました", Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

    // 検索ボタンを押したときに呼び出されるコード
    public void onSearch(View view) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs = null;
        try {
            String[] cols = {"isbn", "title", "price"}; // 検索する列
            String[] params = {txtIsbn.getText().toString()};
            cs = db.query("books", // 対象テーブル
                    cols,          // 検索する列
                    "isbn = ?",    // 条件式
                    params,        // 条件値
                    null,          // グループ　GROUP BY
                    null,          // グループ絞り込み条件 HAVING BY
                    null,          // ソート式 ORDER BY
                    null);         // LIMIT
            if (cs.moveToFirst()) {
                txtTitle.setText(cs.getString(1));
                txtPrice.setText(cs.getString(2));
            } else {
                Toast.makeText(this, "データがありません", Toast.LENGTH_SHORT).show();
            }
        } finally {
            cs.close();
            db.close();
        }
    }

}
