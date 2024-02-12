package com.example.myapplication.components.triggermanager.workers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myapplication.components.other.API_TYPE_SYSTEM
import com.example.myapplication.MainActivity
import com.example.myapplication.components.triggermanager.ContextTrigger
import com.example.myapplication.components.contextProviderProcessor.cds.getBatteryPercentage
import com.example.myapplication.components.contextProviderProcessor.cds.getScreenOnTimestamp
import com.example.myapplication.components.notificationservice.makeStatusNotification
import com.example.myapplication.components.triggermanager.APIPriority
import com.example.myapplication.components.triggermanager.TriggerManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "Worker"

class NotificationWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {

        return withContext(Dispatchers.IO) {
            return@withContext try {
                makeStatusNotification(MainActivity.Constants.context)
                val current = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                Log.d(TAG,"$TAG executed at $current")
//                Log.d(TAG,"Trigger Details : ${contextTrigger.type}, ${contextTrigger.message}, ${contextTrigger.priority}")
                Result.success()
            } catch (throwable: Throwable) {
                Log.e(
                    TAG,
                    "My worker failed",
                    throwable)
                Result.failure()
            }
        }
    }
}

