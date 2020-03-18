package org.woowacourse.lunchbot.controller;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.woowacourse.lunchbot.domain.Restaurants;
import org.woowacourse.lunchbot.service.EatTogetherService;

@EnableScheduling
@Component
public class Scheduler {
    private final EatTogetherService eatTogetherService;
    private final Restaurants restaurants;

    public Scheduler(EatTogetherService eatTogetherService, Restaurants restaurants) {
        this.eatTogetherService = eatTogetherService;
        this.restaurants = restaurants;
    }

    // * : 모두 항상   초 분 시 일 월 요일
    @Scheduled(cron = "0 25 11 * * *")
    private void updateLunchResult() {
        eatTogetherService.updateResults();
    }

    @Scheduled(cron = "0 50 17 * * *")
    private void updateDinnerResult() {
        eatTogetherService.updateResults();
    }

    // 매일 오전 12시
    @Scheduled(cron = "0 0 0 * * *")
    private void updateRestaurantData() {
        restaurants.initializeRestaurants();
    }
}