package samuel.morais.minhassenhas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class EditarSenhaActivity : AppCompatActivity() {
    private lateinit var dao: SenhaDAO
    private lateinit var alterarSenha: Button
    private lateinit var apagarSenha: Button
    private lateinit var cancelar: Button
    private lateinit var maiscula: CheckBox
    private lateinit var especial: CheckBox
    private lateinit var numeros: CheckBox
    private lateinit var seekBar: SeekBar
    private lateinit var atualNumero: TextView
    private lateinit var descricao: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editarsenha)
        this.dao = SenhaDAO(this)


        descricao = findViewById(R.id.etDescricao)
        alterarSenha = findViewById(R.id.btAlterar)
        apagarSenha = findViewById(R.id.btApagar)
        cancelar = findViewById(R.id.btCancelar)
        maiscula = findViewById(R.id.cbMaiusculo)
        especial = findViewById(R.id.cbEspecial)
        numeros = findViewById(R.id.cbNumeros)
        seekBar = findViewById(R.id.seekBar)
        atualNumero = findViewById(R.id.tvTamanhoAtual)

        val senhaId = intent.getIntExtra("senhaId", -1)

        if (senhaId != -1) {
            val senha = dao.find(senhaId)

            if (senha != null) {
                descricao.text = Editable.Factory.getInstance().newEditable(senha.descricao)
                seekBar.progress = senha.getTamanho()
                maiscula.isChecked = senha.verifyMaiusculo()
                numeros.isChecked = senha.verifyNumero()
                especial.isChecked = senha.verifyEspecial()

                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        atualNumero.text = progress.toString()
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }
                })

                alterarSenha.setOnClickListener() {
                    alteraSenha(senha)
                }
                apagarSenha.setOnClickListener() {
                    apagarSenha(senha)
                }
                cancelar.setOnClickListener() {
                    cancelar()
                }
            }
        }

    }

    fun alteraSenha(senha: Password){
        val senhaAlterada = editarSenha(senha)
        dao.update(senhaAlterada)
        finish()
    }
    fun apagarSenha(senha: Password){
        dao.delete(senha.id)
        finish()
    }
    fun cancelar(){
        finish()
    }

    fun editarSenha(novaSenha: Password): Password {
        novaSenha.gerarSenha(seekBar.progress, maiscula.isChecked, numeros.isChecked, especial.isChecked)
        novaSenha.descricao = descricao.text.toString()
        return novaSenha
    }

}