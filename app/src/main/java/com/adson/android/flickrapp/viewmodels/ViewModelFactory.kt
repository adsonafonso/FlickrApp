/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.paging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adson.android.flickrapp.repositories.FlickrRepository
import com.adson.android.flickrapp.viewmodels.FlickrViewModel

/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val repository: FlickrRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlickrViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlickrViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
