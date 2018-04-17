package com.johnmagdalinos.android.newsworld.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.johnmagdalinos.android.newsworld.repository.ArticleRepository;

public class ArticleViewModelFactory implements ViewModelProvider.Factory {
    /** Member variables */
    private ArticleRepository mRepo;

    public ArticleViewModelFactory(ArticleRepository repository) {
        mRepo = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArticleViewModel.class)) {
            return (T) new ArticleViewModel(mRepo);
        } else {
            throw new IllegalArgumentException("Viewmodel not found");
        }
    }
}
