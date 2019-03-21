//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zhidianfan.pig.push.controller.bo;

public interface IRateLimiting {
    int getRateLimitQuota();

    int getRateLimitRemaining();

    int getRateLimitReset();
}
