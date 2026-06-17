package com.example.wikisw.di

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class KoinModulesTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkKoinModules() {
        val combinedModule = module {
            includes(appModules)
        }

        combinedModule.verify()
    }
}