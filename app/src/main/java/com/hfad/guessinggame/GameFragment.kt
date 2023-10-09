package com.hfad.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hfad.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false).apply {
            composeView.setContent {
                MaterialTheme {
                    Surface {
                        GameFragmentContent(viewModel)
                    }
                }
            }
        }
        val view = binding.root
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        //This tells the view model provider to get the GameViewModel object
        //that’s linked to the fragment, or create a new one if it does not already exist

        // dung live data observer roi thi ko dung ham nay nua
        //updateScreen()

        // Dung observe cho tung thuoc tinh cu the, ung voi tung cau trong ham updateScreen()

        // tuy nhien, dung data binding thi fragment khong observe nua ma view observe
        // thuoc tinh live data cua view model de tu cap nhat view
        // nhung chi voi cac view can hien thi trong layout moi dung data binding,
        // viec navigate sang fragment dua vao observe gia tri thuoc tinh gameOver
        // van phai observe tiep, khong thay duoc

        // dung data binding

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.incorrectGuesses.text = "Incorrect guesses: $newValue"
//        })
//
//
//        viewModel.livesLeft.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.lives.text = "You have $newValue lives left"
//        })
//
//        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.word.text = newValue
//        })

        viewModel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
            if (newValue) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        })


        binding.guessButton.setOnClickListener {
            // khi nguoi dung nhap ky tu du doan va bam nut Guess thi goi makeGuess
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            // dung live data thi ko dung ham updateScreen() nua
            // updateScreen()
            // khong dung nua khi da dung viewModel.gameOver.observe o dong 59 o tren
//            if (viewModel.isWon() || viewModel.isLost()) {
//                val action = GameFragmentDirections
//                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
//                view.findNavController().navigate(action)
//            }
        }

        return view
    }
    @Composable
    fun FinishGameButton(clicked: () -> Unit) {
        Button(onClick = clicked) {
            Text("Finish Game")
        }
    }

    @Composable
    fun EnterGuess(guess: String, changed: (String) -> Unit) {
        TextField(
            value = guess,
            label = {Text("Guess a letter")},
            onValueChange = changed
        )
    }

    @Composable
    fun GuessButton(clicked: () -> Unit) {
        Button(onClick = clicked) {
            Text("Guess!")
        }
    }

    @Composable
    fun GameFragmentContent(viewModel: GameViewModel) {
        val guess = remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxWidth()){
            EnterGuess(guess.value) {guess.value = it}
            Column (modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                GuessButton {
                    viewModel.makeGuess(guess.value.uppercase())
                    guess.value = ""
                }
                FinishGameButton {
                    viewModel.finishGame()
                }
            }
        }
    }

//    private fun updateScreen() {
//        binding.word.text = viewModel.secretWordDisplay
//        binding.lives.text = "You have ${viewModel.livesLeft} lives left"
//        // khi incorrectGuesses la thuoc tinh cua viewModel,
//        // de truy cap vao thuoc tinh cua 1 doi tuong thi dung ${...}
//        binding.incorrectGuesses.text = "Incorrect guesses: ${viewModel.incorrectGuesses}"
//        // khi incorrectGuesses thuoc tinh cua fragment thi lay nhu sau
//        //binding.incorrectGuesses.text = "Incorrect guesses: $incorrectGuesses"
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
