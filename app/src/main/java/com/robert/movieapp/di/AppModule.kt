package com.robert.movieapp.di

import com.robert.movieapp.domain.usecase.MovieInteractor
import com.robert.movieapp.domain.usecase.MovieUseCase
import com.robert.movieapp.view.detail.DetailViewModel
import com.robert.movieapp.view.home.MainViewModel
import com.robert.movieapp.view.list.ListMovieViewModel
import com.robert.movieapp.view.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ListMovieViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
