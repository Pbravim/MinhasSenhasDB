package samuel.morais.minhassenhas

import android.content.ContentValues
import android.content.Context
import java.util.Calendar

class SenhaDAO {
    private val banco: BancoHelper

    constructor(context: Context){
        this.banco = BancoHelper(context)
    }

    fun insert(senha: Password){
        val dataHora = Calendar.getInstance().timeInMillis
        val cv = ContentValues()
        cv.put("descricao", senha.descricao)
        cv.put("senha", senha.senha)
        cv.put("created_at", dataHora)
        cv.put("updated_at", dataHora)
        this.banco.writableDatabase.insert("senhas", null, cv)
    }

    fun select(): List<Password>{
        var lista = ArrayList<Password>()
        val colunas = arrayOf("id", "descricao", "senha", "created_at", "updated_at")
        val c = this.banco.readableDatabase.query("senhas", colunas, null, null, null,null, null)

        c.moveToFirst()
        for (i in 1..c.count){
            val id = c.getInt(0)
            val descricao = c.getString(1)
            val senha = c.getString(2)
            val created_at = c.getLong(3)
            val updated_at = c.getLong(4)
            val password = Password(id, descricao, senha, created_at, updated_at)
            lista.add(password)
            c.moveToNext()
        }

        return lista
    }

    fun find(id: Int): Password?{
        val colunas = arrayOf("id", "descricao", "senha", "created_at", "updated_at")
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        val c = this.banco.readableDatabase.query("senhas", colunas, where, pWhere, null,null, null)

        c.moveToFirst()
        if (c.count == 1){
            val id = c.getInt(0)
            val descricao = c.getString(1)
            val senha = c.getString(2)
            val created_at = c.getLong(3)
            val updated_at = c.getLong(4)
            return Password(id, descricao, senha, created_at, updated_at)
        }
        return null
    }

    fun delete(id: Int){
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        this.banco.writableDatabase.delete("senhas", where, pWhere)
    }

    fun update(senha: Password){
        val where = "id = ?"
        val pWhere = arrayOf(senha.id.toString())
        val cv = ContentValues()
        cv.put("descricao", senha.descricao)
        cv.put("senha", senha.senha)
        cv.put("updated_at", Calendar.getInstance().timeInMillis)
        this.banco.writableDatabase.update("senhas", cv, where, pWhere)
    }
}