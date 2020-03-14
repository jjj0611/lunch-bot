package org.woowacourse.lunchbot.slack.fragment.composition.text;

import org.woowacourse.lunchbot.domain.UserProfile;

import java.util.List;
import java.util.stream.Collectors;

public class PlainText extends Text {
    public PlainText() {
    }

    public PlainText(String text) {
        super(TextType.PLAIN_TEXT, text);
    }

    public static PlainText from(List<UserProfile> matchedUserTeam) {
        String names = matchedUserTeam.stream()
                .map(userProfile -> "@" + userProfile.getDisplayName())
                .collect(Collectors.joining(","));
        return new PlainText(names);
    }
}

