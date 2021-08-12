package com.sberkozd.lettervault.notification

import android.content.Context
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotifyWorker(@NonNull context: Context?, @NonNull params: WorkerParameters?) :
    CoroutineWorker(context!!, params!!) {


    @NonNull
    override suspend fun doWork(): Result {
        // Method to trigger an instant notification

        withContext(Dispatchers.Main) {
            Toast.makeText(applicationContext, "NotifyWorker Works!!", Toast.LENGTH_LONG).show()
        }

        return Result.success()
        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)
    }
}