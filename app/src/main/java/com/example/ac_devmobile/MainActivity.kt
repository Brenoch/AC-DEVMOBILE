package com.example.ac_devmobile

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ac_devmobile.adapter.ProdutoAdapter
import com.example.ac_devmobile.model.Produto
import com.example.ac_devmobile.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etNome: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnAtualizar: Button
    private lateinit var btnDeletar: Button
    private lateinit var btnListar: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProdutoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etId = findViewById(R.id.etId)
        etNome = findViewById(R.id.etNome)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnAtualizar = findViewById(R.id.btnAtualizar)
        btnDeletar = findViewById(R.id.btnDeletar)
        btnListar = findViewById(R.id.btnListar)
        recyclerView = findViewById(R.id.recyclerView)

        adapter = ProdutoAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnSalvar.setOnClickListener { salvar() }
        btnAtualizar.setOnClickListener { atualizar() }
        btnDeletar.setOnClickListener { deletar() }
        btnListar.setOnClickListener { listar() }

        // Removido o listar() automático do onCreate
    }

    private fun listar() {
        RetrofitClient.instance.listar().enqueue(object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    val produtos = response.body() ?: emptyList()
                    adapter.updateList(produtos)
                } else {
                    Toast.makeText(this@MainActivity, "Erro ao listar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun salvar() {
        val nome = etNome.text.toString()
        if (nome.isEmpty()) {
            Toast.makeText(this, "Informe o nome", Toast.LENGTH_SHORT).show()
            return
        }

        val produto = Produto(nome = nome)
        RetrofitClient.instance.salvar(produto).enqueue(object : Callback<Produto> {
            override fun onResponse(call: Call<Produto>, response: Response<Produto>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Salvo com sucesso! Clique em Listar para ver.", Toast.LENGTH_SHORT).show()
                    etNome.text.clear()
                    // Limpa a lista atual para forçar o usuário a clicar em listar
                    adapter.updateList(emptyList())
                } else {
                    Toast.makeText(this@MainActivity, "Erro ao salvar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Produto>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun atualizar() {
        val idStr = etId.text.toString()
        val nome = etNome.text.toString()
        if (idStr.isEmpty() || nome.isEmpty()) {
            Toast.makeText(this, "Informe ID e Nome", Toast.LENGTH_SHORT).show()
            return
        }

        val id = idStr.toLong()
        val produto = Produto(id = id, nome = nome)
        RetrofitClient.instance.atualizar(id, produto).enqueue(object : Callback<Produto> {
            override fun onResponse(call: Call<Produto>, response: Response<Produto>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Atualizado com sucesso! Clique em Listar para ver.", Toast.LENGTH_SHORT).show()
                    etId.text.clear()
                    etNome.text.clear()
                    // Limpa a lista atual para forçar o usuário a clicar em listar
                    adapter.updateList(emptyList())
                } else {
                    Toast.makeText(this@MainActivity, "Erro ao atualizar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Produto>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deletar() {
        val idStr = etId.text.toString()
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Informe o ID", Toast.LENGTH_SHORT).show()
            return
        }

        val id = idStr.toLong()
        RetrofitClient.instance.deletar(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Deletado com sucesso! Clique em Listar para ver.", Toast.LENGTH_SHORT).show()
                    etId.text.clear()
                    // Limpa a lista atual para forçar o usuário a clicar em listar
                    adapter.updateList(emptyList())
                } else {
                    Toast.makeText(this@MainActivity, "Erro ao deletar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
