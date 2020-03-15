package org.woowacourse.lunchbot.domain;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class UserMatcher {

    private Set<UserProfile> appliedUsers = new HashSet<>();
    private List<List<UserProfile>> matchedUsers = new ArrayList<>();

    public boolean apply(UserProfile userProfile) {
        return appliedUsers.add(userProfile);
    }

    public List<List<UserProfile>> getMatchedUsers() {
        return matchedUsers;
    }

    public void generateMatchedUsers() {
        if (appliedUsers.isEmpty()) {
            return;
        }
        List<UserProfile> userProfiles = new ArrayList<>(appliedUsers);
        Collections.shuffle(userProfiles);
        LinkedList<List<UserProfile>> matchedUsers = new LinkedList<>(Lists.partition(userProfiles, 4));
        List<UserProfile> lastTeam = matchedUsers.getLast();
        if (matchedUsers.size() != 1 && lastTeam.size() == 1) {
            matchedUsers.removeLast();
            matchedUsers.getLast().addAll(lastTeam);
        }
        this.matchedUsers = matchedUsers;
    }

    public void resetAppliedUsers() {
        appliedUsers = new HashSet<>();
    }

    public void resetMatchedUsers() {
        matchedUsers = new ArrayList<>();
    }
}
