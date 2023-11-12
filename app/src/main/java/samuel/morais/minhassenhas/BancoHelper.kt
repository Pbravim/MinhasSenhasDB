package samuel.morais.minhassenhas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BancoHelper(context: Context): SQLiteOpenHelper(context, "senha.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "create table senhas(" +
                "id integer primary key autoincrement, " +
                "descricao text, " +
                "senha text, " +
                "created_at integer, " +
                "updated_at integer" +
                ")"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, anterior: Int, atual: Int) {
        db?.execSQL("drop table senha")
        this.onCreate(db)
    }
}