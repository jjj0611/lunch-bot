package org.woowacourse.lunchbot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserProfile {

    private Profile profile;

    public String getDisplayName() {
        return profile.getDisplayName();
    }

    public String getImageUrl() {
        return profile.getImage24();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile targetProfile = (UserProfile) o;
        return this.getDisplayName().equals(targetProfile.getDisplayName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDisplayName());
    }

    class Profile {

        private String displayName;
        private String image24;

        public Profile() {
        }

        public Profile(String displayName, String image24) {
            this.displayName = displayName;
            this.image24 = image24;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getImage24() {
            return image24;
        }
    }
}
