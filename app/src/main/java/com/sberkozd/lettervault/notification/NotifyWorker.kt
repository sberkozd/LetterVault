package com.sberkozd.lettervault.notification

import android.content.Context
import androidx.annotation.NonNull
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sberkozd.lettervault.ui.detail.DetailRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class NotifyWorker @AssistedInject constructor(
    @Assisted context: Context?,
    @Assisted params: WorkerParameters?,
    private val detailRepository: DetailRepository
) :
    CoroutineWorker(context!!, params!!) {

    @NonNull
    override suspend fun doWork(): Result {
        // Method to trigger an instant notification

        withContext(Dispatchers.Main) {
            //Toast.makeText(applicationContext, "NotifyWorker Works!!", Toast.LENGTH_LONG).show()
            val noteId = inputData.getInt("noteId", 0)
            if (noteId != 0) {
                val note = detailRepository.getNoteByID(noteId)

                note?.let {

                    it.isLocked = 0

                    detailRepository.updateNote(it)

                    NotificationHelper().sendNoteUnlockedNotification(
                        applicationContext,
                        1,
                        true,
                        name = it.noteTitle,
                        description = it.noteContext
                    )
                }

            }

        }

        return Result.success()
        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)
    }
}