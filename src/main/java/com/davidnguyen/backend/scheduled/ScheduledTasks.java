package com.davidnguyen.backend.scheduled;

import com.davidnguyen.backend.model.Role;
import com.davidnguyen.backend.model.User;
import com.davidnguyen.backend.model.UserRole;
import com.davidnguyen.backend.repository.RolesRepository;
import com.davidnguyen.backend.repository.UserRepository;
import com.davidnguyen.backend.repository.UserRoleRepository;
import com.davidnguyen.backend.utility.constant.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class ScheduledTasks {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final UserRoleRepository userRoleRepository;

    public ScheduledTasks(UserRepository userRepository, RolesRepository rolesRepository,
                          UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Chạy vào 12h đêm mỗi ngày
    public void deleteUsersScheduled() {
        LocalDateTime daysAgo = LocalDateTime.now().minusDays(UserConstant.DELETE_USER_AFTER_DAYS);
        List<User> usersToDelete = userRepository.findAllByDeletedAtBefore(daysAgo);
        List<Role> rolesToDelete = rolesRepository.findAllByDeletedAtBefore(daysAgo);
        List<UserRole> userRolesToDelete = userRoleRepository.findAllByDeletedAtBefore(daysAgo);

        if (!usersToDelete.isEmpty()) {
            userRepository.deleteAll(usersToDelete);
            log.warn("Deleted {} users", usersToDelete.size());
        }

        if (!rolesToDelete.isEmpty()) {
            rolesRepository.deleteAll(rolesToDelete);
            log.warn("Deleted {} roles", rolesToDelete.size());
        }

        if (!userRolesToDelete.isEmpty()) {
            userRoleRepository.deleteAll(userRolesToDelete);
            log.warn("Deleted {} userRoles", userRolesToDelete.size());
        }

        log.info("Deleted schedule finished");
    }
}