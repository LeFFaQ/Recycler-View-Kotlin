package com.lffq.recyclerviewtest.network

object SearchProvider {
    fun provideSearch(): SearchRepository {
        return SearchRepository(NewsService.create())
    }
}