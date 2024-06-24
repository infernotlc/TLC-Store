package com.tlh.afinal.model



import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AwesomeTest {

    @get:Rule
    val hilt = HiltAndroidRule(this)

    @Inject lateinit var storage: StorageService
    @Inject lateinit var firestore: FirebaseFirestore

    @Before
    fun setup() {
        hilt.inject()
        runBlocking { firestore.clearPersistence().await() }
    }

    @Test
    fun test() = runTest {
        val newId = storage.save(Task(title = "Testing"))
        val result = storage.tasks.first()
        assertThat(result).containsExactly(Task(id = newId, title = "Testing"))
    }
}
