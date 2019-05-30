package com.zhidianfan.pig.yd.moduler.resv.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author sjl
 * 2019-05-24 11:27
 */
public class OtherTest {

    @Test
    public void testLocalDate() {
        // 2019-05-24 11:25:46
        // 2019-05-24 11:25:50
        LocalDateTime start = LocalDateTime.of(2019, 5, 24, 11, 25, 46);
        LocalDateTime end = LocalDateTime.of(2019, 5, 24, 11, 25, 50);
        Duration duration = Duration.between(start, end);
        long seconds = duration.getSeconds();
        int s = (int) seconds;
        System.out.println(seconds);
    }

    @AllArgsConstructor
    @Data
    class Sort {
        private Integer id;
        private Integer sort;
        private String key;
    };
    @Test
    public void testSort() {

        List<Sort> sorts = Arrays.asList(
                new Sort(1, 1, "a"),
                new Sort(2, 3, "b"),
                new Sort(3, 2, "c"),
                new Sort(4, 4, "d"),
                new Sort(5, 5, "e"),
                new Sort(6, 9, "f"),
                new Sort(7, 9, "g")
        );
        Optional<Sort> max = sorts.stream()
                .max(Comparator.comparing(Sort::getSort).thenComparing(Sort::getId, Comparator.reverseOrder()));
        System.err.println(max.get());

    }
}
