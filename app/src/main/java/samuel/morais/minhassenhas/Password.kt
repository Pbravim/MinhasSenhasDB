package samuel.morais.minhassenhas

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat

//class Password() : Parcelable {
//    var tamanho = 0
//    var descricao = ""
//    var numero: Boolean = false
//    var especial: Boolean = false
//    var maiusculo: Boolean = false
//    lateinit var senha: String
//
//    constructor(parcel: Parcel) : this() {
//        tamanho = parcel.readInt()
//        descricao = parcel.readString() ?: ""
//        numero = parcel.readByte() != 0.toByte()
//        especial = parcel.readByte() != 0.toByte()
//        maiusculo = parcel.readByte() != 0.toByte()
//        senha = parcel.readString() ?: ""
//
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(tamanho)
//        parcel.writeString(descricao)
//        parcel.writeByte(if (numero) 1 else 0)
//        parcel.writeByte(if (especial) 1 else 0)
//        parcel.writeByte(if (maiusculo) 1 else 0)
//        parcel.writeString(senha)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Password> {
//        override fun createFromParcel(parcel: Parcel): Password {
//            return Password(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Password?> {
//            return arrayOfNulls(size)
//        }
//    }

class Password {
    var id: Int
    var descricao: String
    var senha: String
    var created_at: Long
    var updated_at: Long

    // memória
    constructor(descricao: String){
        this.id = -1
        this.descricao = descricao
        this.senha = ""
        this.created_at = -1
        this.updated_at = -1
    }

    constructor(id: Int, descricao: String, senha: String, created_at: Long, updated_at: Long){
        this.id = id
        this.descricao = descricao
        this.senha = senha
        this.created_at = created_at
        this.updated_at = updated_at
    }

    override fun toString(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        return "${id} ${descricao} ${senha} ${sdf.format(created_at)} ${sdf.format(updated_at)}"    }


    fun gerarSenha(tamanho: Int, maiusculo: Boolean, numero: Boolean, especial:Boolean): String {
        val password = StringBuilder()
        val listaCaracteres = StringBuilder()
        val minuscula = "abcdefghijklmnopqrstuvwxyz"
        val maiuscula = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numeros = "0123456789"
        val especiais = "!@#/$%^&*()_+-=[]{}|;:,.<>?"

        listaCaracteres.append(minuscula)
        if (maiusculo)
            listaCaracteres.append(maiuscula)
        if (numero)
            listaCaracteres.append(numeros)
        if(especial)
            listaCaracteres.append(especiais)

        repeat(tamanho) {
            val randomIndex = (listaCaracteres.indices).random()
            val randomCaracteres = listaCaracteres[randomIndex]
            password.append(randomCaracteres)
        }
        senha = password.toString()
        return senha
    }

    fun getTamanho():Int{
        return this.senha.length
    }

    fun verifyMaiusculo(): Boolean{
        this.senha.forEach {
            if (it.isUpperCase())
                return true
        }
        return false
    }

    fun verifyNumero(): Boolean{
        this.senha.forEach {
            if (it.isDigit())
                return true
        }
        return false
    }
    fun verifyEspecial(): Boolean{
        this.senha.forEach {
            if (!(it.isDigit() || it.isLetter()))
                return true
        }
        return false
    }





}