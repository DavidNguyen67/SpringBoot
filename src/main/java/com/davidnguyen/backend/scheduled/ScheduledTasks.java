package com.davidnguyen.backend.scheduled;

import com.davidnguyen.backend.model.User;
import com.davidnguyen.backend.repository.UserRepository;
import com.davidnguyen.backend.utility.constant.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class ScheduledTasks {
    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Chạy vào 12h đêm mỗi ngày
    public void deleteUsersScheduled() {
        LocalDateTime daysAgo = LocalDateTime.now().minusDays(UserConstant.DELETE_USER_AFTER_DAYS);
        List<User> usersToDelete = userRepository.findAllByDeletedAtBefore(daysAgo);

        if (!usersToDelete.isEmpty()) {
            userRepository.deleteAll(usersToDelete);
            log.warn("Deleted {} users", usersToDelete.size());
            return;
        }
        log.info("No users need to delete");
    }
}