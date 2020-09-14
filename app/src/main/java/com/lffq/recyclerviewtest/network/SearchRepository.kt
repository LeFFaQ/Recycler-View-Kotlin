package com.lffq.recyclerviewtest.network

import io.reactivex.Observable

class SearchRepository(val apiService: NewsService) {
    fun searchUsers(lang: String): Observable<Latest> {
        return apiService.search(lang)
    }
}