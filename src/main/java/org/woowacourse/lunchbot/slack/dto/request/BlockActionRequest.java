package org.woowacourse.lunchbot.slack.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.woowacourse.lunchbot.slack.dto.request.action.Action;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockActionRequest {
    private String triggerId;
    private String responseUrl;
    private List<Action> actions;
    private String type;
    private View view;
    private User user;

    public String getActionId() {
        return actions.get(0).getActionId();
    }

    public String getBlockId() {
        return actions.get(0).getBlockId();
    }

    public String getPrivateMetadata() {
        return view.getPrivateMetadata();
    }

    public String getUserId() {
        return user.getId();
    }

    @Override
    public String toString() {
        return "BlockActionRequest{" +
                "triggerId='" + triggerId + '\'' +
                ", responseUrl='" + responseUrl + '\'' +
                ", actions=" + actions +
                ", type='" + type + '\'' +
                ", view=" + view +
                '}';
    }

    class View {
        private String privateMetadata;

        public View() {
        }

        public View(String privateMetadata) {
            this.privateMetadata = privateMetadata;
        }

        public String getPrivateMetadata() {
            return privateMetadata;
        }
    }

    class User {
        private String id;
        private String userName;
        private String name;
        private String teamId;

        public User() {
        }

        public User(String id, String userName, String name, String teamId) {
            this.id = id;
            this.userName = userName;
            this.name = name;
            this.teamId = teamId;
        }

        public String getId() {
            return id;
        }
    }
}

