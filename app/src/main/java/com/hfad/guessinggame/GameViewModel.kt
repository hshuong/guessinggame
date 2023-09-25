package com.hfad.guessinggame

import androidx.lifecycle.ViewModel
class GameViewModel : ViewModel() {
    val words = listOf("Android", "Activity", "Fragment")
    val secretWord = words.random().uppercase()
    var secretWordDisplay = ""
    var correctGuesses = ""
    var incorrectGuesses = ""
    var livesLeft = 8

    init {
        // ban dau, vi du secretWord la ANDROID, ham deriveSecretWordDisplay se goi
        // checkLetter de kiem tra tung ky tu trong correctGuesses (dang empty) so voi
        // ANDROID. Do correctGuesses ban dau la empty nen deriveSecretWordDisplay tra ve
        // la "_______"
        secretWordDisplay = deriveSecretWordDisplay()
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

    fun isWon(): Boolean = secretWord.equals(secretWordDisplay, true)

    fun isLost(): Boolean = livesLeft <=0

    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You WON!"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord"
        return message
    }
}