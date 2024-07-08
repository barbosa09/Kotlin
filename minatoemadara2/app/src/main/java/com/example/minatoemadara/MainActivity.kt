package com.example.minatoemadara

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.minatoemadara.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var jogadorAtual = "X"
    private var dificuldade = "Fácil"
    private var modoDeJogo = "JogadorVsJogador"
    private val tabuleiro = Array(3) { arrayOf("", "", "") }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        setBoardListeners()

        binding.dificuldadeFacil.setOnClickListener {
            dificuldade = "Fácil"
        }

        binding.dificuldadeDificil.setOnClickListener {
            dificuldade = "Difícil"
        }

        binding.modoJogadorVsJogador.setOnClickListener {
            modoDeJogo = "JogadorVsJogador"
        }

        binding.modoJogadorVsMaquina.setOnClickListener {
            modoDeJogo = "JogadorVsMaquina"
        }
    }

    // Função associada com todos os botões @param view é o botão clicado
    fun buttonClick(view: View) {
        // O botão clicado é associado com uma constante
        val buttonSelecionado = view as Button
        // O texto do botão recebe o jogador atual
        buttonSelecionado.text = jogadorAtual

        // De acordo com o botão clicado, a posição da matriz receberá o jogador
        when (buttonSelecionado.id) {
            binding.buttonZero.id -> tabuleiro[0][0] = jogadorAtual
            binding.buttonUm.id -> tabuleiro[0][1] = jogadorAtual
            binding.buttonDois.id -> tabuleiro[0][2] = jogadorAtual
            binding.buttonTres.id -> tabuleiro[1][0] = jogadorAtual
            binding.buttonQuatro.id -> tabuleiro[1][1] = jogadorAtual
            binding.buttonCinco.id -> tabuleiro[1][2] = jogadorAtual
            binding.buttonSeis.id -> tabuleiro[2][0] = jogadorAtual
            binding.buttonSete.id -> tabuleiro[2][1] = jogadorAtual
            binding.buttonOito.id -> tabuleiro[2][2] = jogadorAtual
        }

        // Verifica o vencedor
        val vencedor = verificaVencedor(tabuleiro)

        if (!vencedor.isNullOrBlank()) {
            Toast.makeText(this, "Vencedor: $vencedor", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Alterna jogador
            jogadorAtual = if (jogadorAtual == "madara") "minato" else "madara"

            // Desabilita o botão selecionado
            buttonSelecionado.isEnabled = false

            // Se for a vez da máquina jogar, faz a jogada da máquina com delay
            if (jogadorAtual == "minato") {
                Handler(Looper.getMainLooper()).postDelayed({
                    jogadaMaquina()
                }, 1000) // 1 segundo de delay
            }
        }
    }

    private fun jogadaMaquina() {
        if (dificuldade == "Fácil") {
            jogadaFacil()
        } else {
            jogadaDificil()
        }
    }

    private fun jogadaFacil() {
        // Procura uma célula vazia para a jogada da máquina
        val movimentosPossiveis = mutableListOf<Pair<Int, Int>>()
        for (i in 0..2) {
            for (j in 0..2) {
                if (tabuleiro[i][j].isEmpty()) {
                    movimentosPossiveis.add(Pair(i, j))
                }
            }
        }

        // Seleciona um movimento aleatório
        if (movimentosPossiveis.isNotEmpty()) {
            val movimento = movimentosPossiveis[Random.nextInt(movimentosPossiveis.size)]
            tabuleiro[movimento.first][movimento.second] = jogadorAtual
            atualizaBotao(movimento.first, movimento.second)
        }
    }

    private fun jogadaDificil() {
        // Implementa uma jogada mais inteligente para a máquina (exemplo simples)
        // Tenta ganhar ou bloquear o jogador
        val movimento = melhorMovimento()
        if (movimento != null) {
            tabuleiro[movimento.first][movimento.second] = jogadorAtual
            atualizaBotao(movimento.first, movimento.second)
        }
    }

    private fun melhorMovimento(): Pair<Int, Int>? {
        // Implementa uma estratégia simples de jogada para o modo difícil
        // Exemplo: Tenta encontrar uma célula que possa bloquear o jogador ou ganhar
        for (i in 0..2) {
            for (j in 0..2) {
                if (tabuleiro[i][j].isEmpty()) {
                    // Simula a jogada do jogador atual
                    tabuleiro[i][j] = jogadorAtual
                    if (verificaVencedor(tabuleiro) == jogadorAtual) {
                        tabuleiro[i][j] = ""
                        return Pair(i, j)
                    }
                    tabuleiro[i][j] = ""
                }
            }
        }

        // Se não encontrou um movimento vencedor, faz um movimento aleatório
        return null
    }

    // Atualiza o botão correspondente à posição no tabuleiro
    private fun atualizaBotao(linha: Int, coluna: Int) {
        when {
            linha == 0 && coluna == 0 -> binding.buttonZero.text = jogadorAtual
            linha == 0 && coluna == 1 -> binding.buttonUm.text = jogadorAtual
            linha == 0 && coluna == 2 -> binding.buttonDois.text = jogadorAtual
            linha == 1 && coluna == 0 -> binding.buttonTres.text = jogadorAtual
            linha == 1 && coluna == 1 -> binding.buttonQuatro.text = jogadorAtual
            linha == 1 && coluna == 2 -> binding.buttonCinco.text = jogadorAtual
            linha == 2 && coluna == 0 -> binding.buttonSeis.text = jogadorAtual
            linha == 2 && coluna == 1 -> binding.buttonSete.text = jogadorAtual
            linha == 2 && coluna == 2 -> binding.buttonOito.text = jogadorAtual
        }
    }

    // Função para associar todos os botões
    private fun setBoardListeners() {
        binding.buttonZero.setOnClickListener { buttonClick(binding.buttonZero) }
        binding.buttonUm.setOnClickListener { buttonClick(binding.buttonUm) }
        binding.buttonDois.setOnClickListener { buttonClick(binding.buttonDois) }
        binding.buttonTres.setOnClickListener { buttonClick(binding.buttonTres) }
        binding.buttonQuatro.setOnClickListener { buttonClick(binding.buttonQuatro) }
        binding.buttonCinco.setOnClickListener { buttonClick(binding.buttonCinco) }
        binding.buttonSeis.setOnClickListener { buttonClick(binding.buttonSeis) }
        binding.buttonSete.setOnClickListener { buttonClick(binding.buttonSete) }
        binding.buttonOito.setOnClickListener { buttonClick(binding.buttonOito) }
    }

    // Função para verificar o vencedor
    fun verificaVencedor(tabuleiro: Array<Array<String>>): String? {
        // Verifica linhas
        for (i in 0..2) {
            if (tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2] && tabuleiro[i][0].isNotEmpty()) {
                return tabuleiro[i][0]
            }
        }

        // Verifica colunas
        for (i in 0..2) {
            if (tabuleiro[0][i] == tabuleiro[1][i] && tabuleiro[1][i] == tabuleiro[2][i] && tabuleiro[0][i].isNotEmpty()) {
                return tabuleiro[0][i]
            }
        }

        // Verifica diagonais
        if (tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2] && tabuleiro[0][0].isNotEmpty()) {
            return tabuleiro[0][0]
        }
        if (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0] && tabuleiro[0][2].isNotEmpty()) {
            return tabuleiro[0][2]
        }

        // Verifica empate
        if (tabuleiro.all { row -> row.all { it.isNotEmpty() } }) {
            return "Empate"
        }

        // Retorna null se não há vencedor
        return null
    }
}
