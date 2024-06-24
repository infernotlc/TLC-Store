package com.tlh.afinal.model.service.module

import com.tlh.afinal.model.service.trace
import kotlinx.coroutines.flow.Flow

class StorageService {
    override val tasks: Flow<List<Task>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                firestore
                    .collection(TASK_COLLECTION)
                    .whereEqualTo(USER_ID_FIELD, user.id)
                    .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
                    .dataObjects()
            }

    fun save(task: Task) {
        }


}