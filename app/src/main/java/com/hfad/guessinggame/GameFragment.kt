package com.hfad.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        //This tells the view model provider to get the GameViewModel object
        //that’s linked to the fragment, or create a new one if it does not already exist

        // dung live data observer roi thi ko dung ham nay nua
        //updateScreen()
        // Dung observe cho tung thuoc tinh cu the, ung voi tung cau trong ham updateScreen()
        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer { newValue ->
            binding.incorrectGuesses.text = "Incorrect guesses: $newValue"
        })


        viewModel.livesLeft.observe(viewLifecycleOwner, Observer { newValue ->
            binding.lives.text = "You have $newValue lives left"
        })

        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer { newValue ->
            binding.word.text = newValue
        })


        binding.guessButton.setOnClickListener {
            // khi nguoi dung nhap ky tu du doan va bam nut Guess thi goi makeGuess
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            // dung live data thi ko dung ham updateScreen() nua
            // updateScreen()
            if (viewModel.isWon() || viewModel.isLost()) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        return view
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