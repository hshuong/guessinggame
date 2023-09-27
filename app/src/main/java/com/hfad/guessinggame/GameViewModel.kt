package com.hfad.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class GameViewModel : ViewModel() {
    private val words = listOf("Android", "Activity", "Fragment")
    private val secretWord = words.random().uppercase()
    //var secretWordDisplay = ""
    // nho la var duoc thay bang val nhe. vi chuyen tu viec dung new reference moi
    // khi thay doi gia tri cua secretWordDisplay (thi tao ra 1 doi tuong moi va tro den) sang
    // viec giu nguyen reference ban dau (var secretWordDisplay => val secretWordDisplay)
    //val secretWordDisplay = MutableLiveData<String>()
    private val _secretWordDisplay = MutableLiveData<String>()
    val secretWordDisplay: LiveData<String>
        get() = _secretWordDisplay
    private var correctGuesses = ""
    //var incorrectGuesses = ""
    //val incorrectGuesses = MutableLiveData<String>("")
    private val _incorrectGuesses = MutableLiveData<String>("")
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses

    //var livesLeft = 8
    // val livesLeft = MutableLiveData<Int>(8)
    private val _livesLeft = MutableLiveData<Int>(8)
    val livesLeft : LiveData<Int>
        get() = _livesLeft

    private val _gameOver = MutableLiveData<Boolean>(false)
    val gameOver : LiveData<Boolean>
        get() = _gameOver


    init {
        // ban dau, vi du secretWord la ANDROID, ham deriveSecretWordDisplay se kiem tra
        // tung ky tu cua secretWord bang cach goi ham checkLetter
        // de kiem tra tung ky tu trong correctGuesses (dang la empty) so voi tu
        // ANDROID. Do correctGuesses ban dau la empty nen deriveSecretWordDisplay tra ve
        // la "_______"
        //secretWordDisplay = deriveSecretWordDisplay()
        //secretWordDisplay.value = deriveSecretWordDisplay()
        _secretWordDisplay.value = deriveSecretWordDisplay()
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

    fun makeGuess(guess: String) {
        if (guess.length == 1) { // chi doan 1 ky tu thoi
            // neu ky tu nay doan dung, tu bi mat secretWord co chua ky tu nguoi dung vua nhap
            if (secretWord.contains(guess)) {
                // bo sung ky tu doan dung vao String cac ky tu da doan dung
                correctGuesses += guess
                // cap nhat lai word hien thi mot phan khi nguoi dung doan dung 1 ky tu cua word bi mat
                // secretWordDisplay = deriveSecretWordDisplay()

                // secretWordDisplay.value = deriveSecretWordDisplay()
                _secretWordDisplay.value = deriveSecretWordDisplay()
            } else {
                // ky tu nay nguoi dung doan sai
                // ghep ky tu bi doan sai nay vao String cac ky tu doan sai
                // incorrectGuesses += guess
                // incorrectGuesses.value += guess
                _incorrectGuesses.value += guess
                // giam mang, so lan duoc doan di
                // livesLeft --
                // phai kiem tra null vi value cua MutableLiveData<Int> co the null
                // livesLeft.value = livesLeft.value?.minus(1)
                _livesLeft.value = _livesLeft.value?.minus(1)
            }
            if (isWon() || isLost()) _gameOver.value = true
        }
    }

    private fun isWon(): Boolean = secretWord.equals(secretWordDisplay.value, true)

    //fun isLost(): Boolean = livesLeft <=0
    private fun isLost(): Boolean = livesLeft.value ?:0 <=0

    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You WON!"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord"
        return message
    }
}