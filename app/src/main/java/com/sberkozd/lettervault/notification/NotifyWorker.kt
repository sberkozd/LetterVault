package com.sberkozd.lettervault.notification

class NotifyWorker(@NonNull context: Context?, @NonNull params: WorkerParameters?) :
    Worker(context, params) {
    @NonNull
    fun doWork(): Result {
        // Method to trigger an instant notification
        triggerNotification()
        return Result.success()
        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)
    }
}