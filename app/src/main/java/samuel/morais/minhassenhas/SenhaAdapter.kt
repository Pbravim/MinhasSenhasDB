package samuel.morais.minhassenhas

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class SenhaAdapter(context: Context, resource: Int, objects: List<Password>) :
    ArrayAdapter<Password>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertView =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val descricaoTextView: TextView = convertView.findViewById(R.id.descricaoTextView)
        val tamanhoTextView: TextView = convertView.findViewById(R.id.tamanhoTextView)

        val senha = getItem(position)
        descricaoTextView.text = senha?.descricao
        tamanhoTextView.text = "(${senha?.getTamanho()})"
        return convertView
    }
    }
