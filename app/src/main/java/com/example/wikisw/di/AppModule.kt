package com.example.wikisw.di

import androidx.room.Room
import com.example.wikisw.data.api.StarWarsApi
import com.example.wikisw.data.cache.CharacterDatabase
import com.example.wikisw.data.repository.StarWarsRepositoryImpl
import com.example.wikisw.domain.repository.StarWarsRepository
import com.example.wikisw.domain.usecase.GetCharactersUseCase
import com.example.wikisw.domain.usecase.GetPlanetNameUseCase
import com.example.wikisw.domain.usecase.GetSpeciesNameUseCase
import com.example.wikisw.domain.usecase.ToggleFavoriteUseCase
import com.example.wikisw.presentation.characters.CharactersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://swapi.info/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<StarWarsApi> {
        get<Retrofit>().create(StarWarsApi::class.java)
    }
}

val databaseModule = module {
    single<CharacterDatabase> {
        Room.databaseBuilder(
            androidContext(),
            CharacterDatabase::class.java,
            "wikisw_database"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single { get<CharacterDatabase>().characterDao() }
}

val repositoryModule = module {
    single<StarWarsRepository> { StarWarsRepositoryImpl(api = get(), dao = get()) }
}

val useCaseModule = module {
    factory { GetCharactersUseCase(repository = get()) }
    factory { GetPlanetNameUseCase(repository = get()) }
    factory { GetSpeciesNameUseCase(repository = get()) }
    factory { ToggleFavoriteUseCase(repository = get()) }
}

val viewModelModule = module {
    viewModel {
        CharactersViewModel(
            getCharactersUseCase = get(),
            getPlanetNameUseCase = get(),
            getSpeciesNameUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }
}

val appModules = listOf(networkModule, databaseModule, repositoryModule, useCaseModule, viewModelModule)