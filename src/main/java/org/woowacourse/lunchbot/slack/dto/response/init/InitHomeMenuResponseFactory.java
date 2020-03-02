package org.woowacourse.lunchbot.slack.dto.response.init;

import org.woowacourse.lunchbot.slack.fragment.block.ActionsBlock;
import org.woowacourse.lunchbot.slack.fragment.block.Block;
import org.woowacourse.lunchbot.slack.fragment.block.DividerBlock;
import org.woowacourse.lunchbot.slack.fragment.block.SectionBlock;
import org.woowacourse.lunchbot.slack.fragment.composition.text.MrkdwnText;
import org.woowacourse.lunchbot.slack.fragment.composition.text.PlainText;
import org.woowacourse.lunchbot.slack.fragment.element.ButtonElement;
import org.woowacourse.lunchbot.slack.fragment.element.Element;
import org.woowacourse.lunchbot.slack.fragment.element.ImageElement;
import org.woowacourse.lunchbot.slack.fragment.view.HomeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitHomeMenuResponseFactory {
    public static HomeMenuResponse of(String userId) {
        List<Block> blocks = new ArrayList<>();

        blocks.add(new SectionBlock(
                new MrkdwnText("안녕하세요!\n우아한테크코스 점심봇입니다.:santa:\n즐거운 연말 보내세요~ :wave:"),
                new ImageElement("https://api.slack.com/img/blocks/bkb_template_images/notifications.png",
                        "calendar thumbnail")
        ));
        blocks.add(new DividerBlock());

        List<Element> elements = Arrays.asList(
                new ButtonElement(new PlainText(":spiral_calendar_pad: 오늘 뭐 먹지?"), "recommand"),
                new ButtonElement(new PlainText(":rice: 한식"), "korean"),
                new ButtonElement(new PlainText(":dumpling: 중식"), "chinese"),
                new ButtonElement(new PlainText(":cut_of_meat: 양식"), "western"),
                new ButtonElement(new PlainText(":curry: 인도"), "india"),
                new ButtonElement(new PlainText(":ramen: 베트남"), "vietnam"),
                new ButtonElement(new PlainText(":sushi: 일식"), "japanese"));


        blocks.add(new ActionsBlock("initial_block", elements));

        return new HomeMenuResponse(userId, new HomeView(blocks));
    }

}
