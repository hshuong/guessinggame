package com.hfad.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.hfad.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!


    val words = listOf("Android", "Activity", "Fragment")
    val secretWord = words.random().uppercase()

    var secretWordDisplay = ""
    var correctGuesses = ""
    var incorrectGuesses = ""
    var livesLeft = 8

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        secretWordDisplay = deriveSecretWordDisplay()
        updateScreen()

        binding.guessButton.setOnClickListener {
            // khi nguoi dung nhap ky tu du doan va bam nut Guess thi goi makeGuess
            makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            updateScreen()
            if (isWon() || isLost()) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        return view
    }

    private fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You WON!"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord"
        return message
    }

    // Hien thi tu bi mat duoi dang 1 phan cua tu day du. cac ky tu chua doan duoc hien dau _,
    // cac ky tu da doan duoc hien len dung vi tri cua ky tu trong tu bi mat secretWord
    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            // Kiem tra tung ky tu co trong tu bi mat secretWord, vi du secretWord la ANDROID
            // kiem tra tu A, N, D, R, O, I, D
            display += checkLetter(it.toString())
            // Ghep lai cac ky tu duoc kiem tra thanh tu da doan duoc de hien thi
            // Vi du: AND___D
        }
        return display
    }
    // Kiem tra xem Ky tu str (chi 1 ky tu trong secretWord) co duoc tu correctGuesses
    // chua o ben trong no khong?
    // correctGuesses la cac ky tu nguoi dung doan dung duoc ghep lai voi nhau thanh mot string
    // vi du correctGuesses la "DAN".
    private fun checkLetter(str: String) = when (correctGuesses.contains(str)) {
                                                true -> str
                                                false -> "_"
                                           }

    private fun makeGuess(guess: String) {
        if (guess.length == 1) { // chi doan 1 ky tu thoi
            // neu ky tu nay doan dung, tu bi mat secretWord co chua ky tu nguoi dung vua nhap
            if (secretWord.contains(guess)) {
                // bo sung ky tu doan dung vao String cac ky tu da doan dung
                correctGuesses += guess
                // cap nhat lai tu hien thi mot phan nguoi dung doan dung cua tu bi mat
                secretWordDisplay = deriveSecretWordDisplay()
            } else {
                // ky tu nay doan sai
                // ghep ky tu doan sai vao String cac ky tu doan sai
                incorrectGuesses += guess
                // giam mang, so lan duoc doan di
                livesLeft --
            }
        }
    }

    private fun isWon(): Boolean = secretWord.equals(secretWordDisplay, true)

    private fun isLost(): Boolean = livesLeft <=0

    private fun updateScreen() {
        binding.word.text = secretWordDisplay
        binding.lives.text = "You have $livesLeft lives left"
        binding.incorrectGuesses.text = "Incorrect guesses: $incorrectGuesses"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}