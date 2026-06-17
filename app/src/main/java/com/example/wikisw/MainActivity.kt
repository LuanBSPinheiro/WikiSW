package com.example.wikisw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.wikisw.domain.model.Character
import com.example.wikisw.presentation.characters.CharacterInfoScreen
import com.example.wikisw.presentation.characters.CharactersScreen
import com.example.wikisw.presentation.ui.theme.WikiSWTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WikiSWTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedCharacter by remember { mutableStateOf<Character?>(null) }

                    if (selectedCharacter == null) {
                        CharactersScreen(
                            onCharacterClick = { character ->
                                selectedCharacter = character
                            }
                        )
                    } else {
                        BackHandler { selectedCharacter = null }
                        CharacterInfoScreen(character = selectedCharacter!!)
                    }
                }
            }
        }
    }
}