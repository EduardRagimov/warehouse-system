package com.warehouse.auth_service.entity;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final List<User> members;

    public Group() {
        members = new ArrayList<>();
    }

    public Group(List<User> users)
    {
        members = users;
    }

    public List<User> getMembers() {
        return members;
    }

    public void addMember(User newMember) {
        members.add(newMember);
    }

    public void addMembers(List<User> members) {
        members.addAll(members);
    }
}
