package com.example.leaderboard;

import com.example.leaderboard.service.RankingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class SimpleTest {

    @Autowired
    private RankingService rankingService;

    @Test
    void getRanks() {
        rankingService.getTopRank(1);


        Instant before = Instant.now();
        Long userRank = rankingService.getUserRanking("user_100");
        Duration duration = Duration.between(before, Instant.now());

        System.out.println(String.format("Rank(%d) - Took %d ms", userRank, duration.getNano() / 1000000 + " ms"));

        before = Instant.now();
        List<String> topRankers = rankingService.getTopRank(10);
        duration = Duration.between(before, Instant.now());

        System.out.println(String.format("Range - Took %d ms", duration.getNano() / 1000000 + " ms"));
    }

    @Test
    void insertScore() {
        for (int i = 0; i < 1000000; i++) {
            int score = (int)(Math.random() * 1000000); // 0 ~ 999999
            String userId = "user_" + i;
            rankingService.setUserSocre(userId, score);
        }
    }

    @Test
    void inMemorySortPerformance() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            int score = (int)(Math.random() * 1000000); // 0 ~ 999999
            list.add(score);
        }

        Instant before = Instant.now(); 
        Collections.sort(list); // n * log * n
        Duration duration = Duration.between(before, Instant.now());
        System.out.println(duration.getNano() / 1000000 + " ms");
    }
}
