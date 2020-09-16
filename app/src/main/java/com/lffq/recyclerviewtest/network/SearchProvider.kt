package com.lffq.recyclerviewtest.network


/**
 * Бизнес-логика
 * (Местный синглтон)
 */
object SearchProvider {
    fun provideSearch(): SearchRepository {
        return SearchRepository(NewsService.create())
    }
}