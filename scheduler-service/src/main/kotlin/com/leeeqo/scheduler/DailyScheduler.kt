package com.leeeqo.scheduler

import com.leeeqo.producer.KafkaProducer
import com.leeeqo.service.TaskService
import com.leeeqo.service.UserService
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
@EnableScheduling
class DailyScheduler(
    private val userService: UserService,
    private val taskService: TaskService,
    private val producer: KafkaProducer
) {

    @Scheduled(cron = "0 0 0 * * *")
    fun sendDailyReport() {
        val users = userService.getAllUsers()

        // TODO async
        for (user in users) {
            val summary = taskService.getDailySummaryByUser(user)

            producer.sendSummary(summary)
        }
    }
}
