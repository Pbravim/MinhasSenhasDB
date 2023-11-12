package samuel.morais.minhassenhas

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private lateinit var dao: SenhaDAO
    private lateinit var listView: ListView
    private lateinit var addButton: FloatingActionButton
    private val senhaList: MutableList<Password> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        addButton = findViewById(R.id.ButtonAdd)
        this.dao = SenhaDAO(this)

        val adapter = SenhaAdapter(this, R.layout.list_item, senhaList)
        listView.adapter = adapter



        this.addButton = this.findViewById(R.id.ButtonAdd)


                addButton.setOnClickListener {
                    val intent = Intent(this@MainActivity, Activity_NovaSenha::class.java)
                    startActivity(intent)
                }
                //COPIAR SENHA
                listView.setOnItemLongClickListener { _, _, position, _ ->
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("simple text", senhaList[position].senha)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this@MainActivity, "Senha copiada para a área de transferência", Toast.LENGTH_SHORT).show()
                    true
                }
                listView.setOnItemClickListener { _, _, position, _ ->
                    val senhaSelecionada = senhaList[position]
                    val intent = Intent(this@MainActivity, EditarSenhaActivity::class.java)
                    intent.putExtra("senhaId", senhaSelecionada.id)
                    startActivity(intent)
                }


            }

    override fun onResume() {
        super.onResume()
        updatePasswordList()
    }


    private fun updatePasswordList() {
        senhaList.clear()
        senhaList.addAll(dao.select())
        val adapter = SenhaAdapter(this, R.layout.list_item, senhaList)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
    }
