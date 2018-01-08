package sample.databasebasic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by houxianliangyi on 2018/01/08.
 */

public class SimpleDatabaseHelper extends SQLiteOpenHelper {
    static final private String DBNAME = "simple.sqlite";
    static final private int VERSION = 1;

    // コンストラクター
    public SimpleDatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE books (" +
                "isbn TEXT PRIMARY KEY, title TEXT, price INTEGER)");
        db.execSQL("INSERT INTO books (isbn, title, price)" +
                "VALUES('978-4-7980-4179-7', 'ASP.NET MVC 5', 3500)");
        db.execSQL("INSERT INTO books (isbn, title, price)" +
                "VALUES('978-4-7980-7984-1', 'Swiftポケットリファレンス', 2680)");
        db.execSQL("INSERT INTO books (isbn, title, price)" +
                "VALUES('978-4-7980-3547-5', '独習・学習・自習って何が違うのさ？', 1500)");
    }

    // データベースをバージョンアップしたとき、テーブルを再作成
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS books");
        onCreate(db);
    }

}
