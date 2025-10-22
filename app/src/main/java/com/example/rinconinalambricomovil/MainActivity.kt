package com.example.rinconinalambricomovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*


import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Surface

import com.example.elrinconinalambrico.ui.screens.MenuScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            Surface(color = MaterialTheme.colorScheme.background) {

                MenuScreen()

            }

        }

    }

}

