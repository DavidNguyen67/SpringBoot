package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.model.Role;
import com.davidnguyen.backend.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDTO extends User {
    private List<Role> roles;
}
