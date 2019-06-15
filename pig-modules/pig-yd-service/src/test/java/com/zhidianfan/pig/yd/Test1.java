package com.zhidianfan.pig.yd;

import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.constant.ClientId;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ConfigHotel;
import org.apache.velocity.runtime.parser.node.MathUtils;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/30
 * @Modified By:
 */

public class Test1 {

    private String url = "http://47.99.14.92:9999/auth/oauth/token";

    @Test
    public void test43() {
        int nThreads = 500;
        Stream<Integer> limit = Stream.iterate(0, n -> n + 2)
                .limit(200);
        ExecutorService executorService = new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        LocalDateTime start = LocalDateTime.now();
//        String property = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
//        System.out.println(property);
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","100");
        List<Integer> collect = limit.map(count -> CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("等待开始");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("等待结束一个");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return count;
        }, executorService))
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        LocalDateTime end = LocalDateTime.now();
        System.out.println("执行结束,耗时："+(Duration.between(start,end).getSeconds()+"秒"));

        executorService.shutdown();


    }

    @Test
    public void test42() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        localDate = localDate.minusDays(1);
        System.out.println(localDate);
    }

    @Test
    public void test41() {
        LocalTime startTime1 = LocalTime.of(23, 0, 0);
        LocalTime startTime2 = LocalTime.of(8, 0, 0);
        LocalTime nowTime = LocalTime.now();
        boolean f1 = nowTime.isAfter(startTime1) || nowTime.isBefore(startTime2);
        System.out.println(f1);

    }

    @Test
    public void test3() {
        AtomicInteger count = new AtomicInteger(0);
        count.addAndGet(1);
        int i = count.addAndGet(1);
        System.out.println(i);

    }

    @Test
    public void test1() {
        ClientId client = ClientId.valueOf("PAD");
        System.out.println(client);
        ClientId clientId = ClientId.valueOf("pad");
        System.out.println(clientId);
    }

    @Test
    public void test2() throws UnsupportedEncodingException {

        Map<String, Object> tokenMap = this.getToken("OLD_SERVER", "OLD_SERVER", "older_server", "111111");
        System.out.println(JsonUtils.obj2Json(tokenMap));

        long s = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            tokenMap = this.refreshToken("OLD_SERVER", "OLD_SERVER", (String) tokenMap.get("refresh_token"));
            System.out.println(JsonUtils.obj2Json(tokenMap));
        }
        long e = System.currentTimeMillis();

        System.out.println(e - s);


    }

    /**
     * 获取token
     *
     * @param clientId
     * @param clientMaster
     * @param username
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map<String, Object> getToken(String clientId, String clientMaster, String username, String password) throws UnsupportedEncodingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = Base64.getEncoder().encodeToString((clientId + ":" + clientMaster).getBytes("utf8"));
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.add("Authorization", "Basic " + auth);//各个客户端使用自己的认证信息


        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("username", username);
        param.add("password", password);
        param.add("grant_type", "password");
        param.add("scope", "server");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(param, httpHeaders);


        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url,
                requestEntity, Map.class, param);

        return responseEntity.getBody();
    }

    /**
     * 刷新token
     *
     * @param clientId
     * @param clientMaster
     * @param refreshToken
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map<String, Object> refreshToken(String clientId, String clientMaster, String refreshToken) throws UnsupportedEncodingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = Base64.getEncoder().encodeToString((clientId + ":" + clientMaster).getBytes("utf8"));
        httpHeaders.add("Authorization", "Basic " + auth);//各个客户端使用自己的认证信息
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");


        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("refresh_token", refreshToken);
        param.add("grant_type", "refresh_token");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(param, httpHeaders);


        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url,
                requestEntity, Map.class, param);

        return responseEntity.getBody();
    }


    @Test
    public void test() {
        Date date = new Date(2019 - 1900, 4, 20);
        Date date1 = new Date(2019 - 1900, 4, 23);
        Date date2 = new Date(2019 - 1900, 4, 31);
        List<Date> dateList = Arrays.asList(date, date1, date2);
        Date date3 = dateList.stream()
                .max(Comparator.naturalOrder())
                .get();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(date3));

    }

    @Test
    public void test4() {
        String date = "2019-6-2";
        LocalDate parseDate = LocalDate.parse(date);


        System.out.println(parseDate);
    }

    @Test
    public void test5() {
        int a = 100;
        double b = 5.1;
        Number divide = MathUtils.divide(a, b);
        System.out.println(divide.intValue());
    }

    @Test
    public void test6() {
        List<ConfigHotel> configHotelList = new ArrayList<ConfigHotel>() {
            {
                add(getConfigHotel(1138795410346000385L, "active_sleep_between", "30"));
                add(getConfigHotel(1138795410354388994L, "sleep_loss_between", "60"));
                add(getConfigHotel(1138795410358583298L, "value_category_cycle", "60"));
                add(getConfigHotel(1138795410362777602L, "black_list_order_num", "3"));
            }
        };
        Map<String, String> map = configHotelList.stream()
                .collect(Collectors.toMap(ConfigHotel::getK, ConfigHotel::getV));

        System.out.println(map);
        map.forEach((k, v) -> System.out.println(k + "-----" + v));

//        1138795410346000385	1	active_sleep_between	30	init	1	2019-06-12 21:09:18	2019-06-12 21:09:18
//        1138795410354388994	1	sleep_loss_between	60	init	1	2019-06-12 21:09:18	2019-06-12 21:09:18
//        1138795410358583298	1	value_category_cycle	60	init	1	2019-06-12 21:09:18	2019-06-12 21:09:18
//        1138795410362777602	1	black_list_order_num	3	init	1	2019-06-12 21:09:18	2019-06-12 21:09:18
    }

    @Test
    public void test7() {
        // 2018-04-06
        Date date = new Date(2018 - 1900, 3, 6);
        Instant instant = date.toInstant();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate date1 = localDateTime.toLocalDate();
        System.out.println(date1.format(DateTimeFormatter.ISO_LOCAL_DATE));
        String format = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(format);
        Period period = Period.between(localDateTime.toLocalDate(), LocalDate.now());
        Duration duration = Duration.between(localDateTime, LocalDateTime.now());
        System.out.println(period.getDays());
        System.out.println(duration.toDays());
    }

    private ConfigHotel getConfigHotel(long id, String k, String v) {
        ConfigHotel configHotel = new ConfigHotel();
        configHotel.setId(id);
        configHotel.setHotelId(1L);
        configHotel.setK(k);
        configHotel.setV(v);
        configHotel.setDescri("init");
        configHotel.setFlag(1);
        configHotel.setCreateTime(LocalDateTime.now());
        configHotel.setUpdateTime(LocalDateTime.now());
        return configHotel;
    }

    @Test
    public void test8() {
        Number divide = MathUtils.divide(10, 2.1);
        double v = divide.doubleValue();
        System.out.println(v);
        BigDecimal bigDecimal = new BigDecimal(v).setScale(1, RoundingMode.HALF_UP);
        double v1 = bigDecimal.doubleValue();
        System.out.println(v1);
    }

    @Test
    public void test9() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 4, 2, 1, 1, 1);
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, 4, 5, 1, 1, 1);
        long until1 = localDateTime.until(localDateTime1, ChronoUnit.YEARS);
        System.err.println(until1);
    }

    @Test
    public void test10() {
        Integer customerPersonAvgStart = 2, customerPersonAvgEnd = -1, personAvg = 5;
        Integer customerTotalStart = null, customerTotalEnd = null;
        double customerAmount = -1;
        Integer customerCountStart = null, customerCountEnd = null, customerCount = 0;

        boolean b = lossValue(customerPersonAvgStart, customerPersonAvgEnd, personAvg,
                customerTotalStart, customerTotalEnd, customerAmount,
                customerCountStart, customerCountEnd, customerCount);
        if (b) {
            System.out.println("vip");
        }
    }


    public boolean lossValue(Integer customerPersonAvgStart, Integer customerPersonAvgEnd, int personAvg,
                             Integer customerTotalStart, Integer customerTotalEnd, double customerAmount,
                             Integer customerCountStart, Integer customerCountEnd, int customerCount) {
        // 人均消费
        boolean customerPersonAvConfig = getCustomerPersonAvgConfig(customerPersonAvgStart, customerPersonAvgEnd, personAvg);
        // 消费总金额
        boolean customerTotalConfig = getCustomerTotalConfig(customerTotalStart, customerTotalEnd, customerAmount);
        // 消费次数
        boolean customerCountConfig = getCustomerCountConfig(customerCountStart, customerCountEnd, customerCount);
        return customerPersonAvConfig && customerTotalConfig && customerCountConfig;
    }

    private boolean getCustomerPersonAvgConfig(Integer customerPersonAvgStart, Integer customerPersonAvgEnd, int personAvg) {
        int flag = 0;
        if (customerPersonAvgStart > 0) {
            flag = 1;
        }
        if (customerPersonAvgEnd > 0) {
            flag += 2;
        }

        if (flag == 0) {
            // 两个都没有值
            return true;
        } else if (flag == 1) {
            // 配置了起始值，没有配置结束值
            if (personAvg >= customerPersonAvgStart) {
                return true;
            }
        } else if (flag == 2) {
            // 配置了结束值，没有配置起始值
            if (personAvg <= customerPersonAvgEnd) {
                return true;
            }
        } else {
            // 两个都有配置值
            if (personAvg >= customerPersonAvgStart && personAvg <= customerPersonAvgEnd) {
                return true;
            }
        }
        return false;
    }

    private boolean getCustomerTotalConfig(Integer customerTotalStart, Integer customerTotalEnd, double customerAmount) {
        int flag = 0;
        if (customerTotalStart > 0) {
            flag = 1;
        }
        if (customerTotalEnd > 0) {
            flag += 2;
        }

        if (flag == 0) {
            // 两个都没有值
            return true;
        } else if (flag == 1) {
            // 配置了起始值，没有配置结束值
            if (customerAmount >= customerTotalStart) {
                return true;
            }
        } else if (flag == 2) {
            // 配置了结束值，没有配置起始值
            if (customerAmount <= customerTotalEnd) {
                return true;
            }
        } else {
            // 两个都没有配置值
            if (customerAmount >= customerTotalStart && customerAmount <= customerTotalEnd) {
                return true;
            }
        }
        return false;
    }

    private boolean getCustomerCountConfig(Integer customerCountStart, Integer customerCountEnd, int customerCount) {
        int flag = 0;
        if (customerCountStart > 0) {
            flag = 1;
        }
        if (customerCountEnd > 0) {
            flag += 2;
        }

        if (flag == 0) {
            // 两个都没有配置值
            return true;
        } else if (flag == 1) {
            // 配置了起始值，没有配置结束值
            if (customerCount >= customerCountStart) {
                return true;
            }
        } else if (flag == 2) {
            // 配置了结束值，没有配置起始值
            if (customerCount <= customerCountEnd) {
                return true;
            }
        } else {

            // 两个都有值
            if (customerCount >= customerCountStart && customerCount <= customerCountEnd) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void test11() {
        Integer a = null;
        Integer b = 1;
        System.out.println(b.equals(a));
    }
}
