package com.zhidianfan.pig.yd.moduler.welcrm.dto;

import java.util.List;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/10/22
 * @Modified By:
 */
public class FiveiUserDTO {

    /**
     * consuVo : {"code":"13777575146","consuCodeType":null,"consuDetailList":[{"employeeDiscountLevel":null,"isEmployee":null,"memberAmount":0,"memberCardListVo":[{"amount":0,"code":"13777575146","codeType":"预存款","couponNum":null,"id":26516430,"lockMemberId":null,"rewardAmount":0,"score":null,"type":3}],"memberDiscount":2,"memberId":27496777,"memberLevelId":13,"memberLevelName":"粉丝会员","memberRegTime":"2018-10-22 16:04:59","memberRewardAmount":0,"memberScore":0,"memberTypeName":"手机会员","name":null,"relatedCardCode":null}],"couponList":[],"errorMsg":null,"isConsuScore":1,"isCouponKnockConsu":1,"isUsePwd":null,"memberConsuRuleList":[{"amount":5,"awardType":2,"giftAmount":1,"mostAmount":100,"payType":"1,2,3,4,5,6,7,","way":2}],"memberName":null,"mpcResultMsg":null,"openid":"owvpLt7pO-_EQXEMJj8E2MvUUmOQ","rechargeRuleList":["预存款充值10.00返1.00积分","预存款充值2000.00返450.00奖励","预存款充值1000.00返200.00奖励","预存款充值600.00返100.00奖励"]}
     * result : {"code":0,"msg":"success"}
     */

    private ConsuVoBean consuVo;
    private ResultBean result;

    public ConsuVoBean getConsuVo() {
        return consuVo;
    }

    public void setConsuVo(ConsuVoBean consuVo) {
        this.consuVo = consuVo;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ConsuVoBean {
        /**
         * code : 13777575146
         * consuCodeType : null
         * consuDetailList : [{"employeeDiscountLevel":null,"isEmployee":null,"memberAmount":0,"memberCardListVo":[{"amount":0,"code":"13777575146","codeType":"预存款","couponNum":null,"id":26516430,"lockMemberId":null,"rewardAmount":0,"score":null,"type":3}],"memberDiscount":2,"memberId":27496777,"memberLevelId":13,"memberLevelName":"粉丝会员","memberRegTime":"2018-10-22 16:04:59","memberRewardAmount":0,"memberScore":0,"memberTypeName":"手机会员","name":null,"relatedCardCode":null}]
         * couponList : []
         * errorMsg : null
         * isConsuScore : 1
         * isCouponKnockConsu : 1
         * isUsePwd : null
         * memberConsuRuleList : [{"amount":5,"awardType":2,"giftAmount":1,"mostAmount":100,"payType":"1,2,3,4,5,6,7,","way":2}]
         * memberName : null
         * mpcResultMsg : null
         * openid : owvpLt7pO-_EQXEMJj8E2MvUUmOQ
         * rechargeRuleList : ["预存款充值10.00返1.00积分","预存款充值2000.00返450.00奖励","预存款充值1000.00返200.00奖励","预存款充值600.00返100.00奖励"]
         */

        private String code;
        private Object consuCodeType;
        private Object errorMsg;
        private int isConsuScore;
        private int isCouponKnockConsu;
        private Object isUsePwd;
        private Object memberName;
        private Object mpcResultMsg;
        private String openid;
        private List<ConsuDetailListBean> consuDetailList;
        private List<?> couponList;
        private List<MemberConsuRuleListBean> memberConsuRuleList;
        private List<String> rechargeRuleList;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Object getConsuCodeType() {
            return consuCodeType;
        }

        public void setConsuCodeType(Object consuCodeType) {
            this.consuCodeType = consuCodeType;
        }

        public Object getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(Object errorMsg) {
            this.errorMsg = errorMsg;
        }

        public int getIsConsuScore() {
            return isConsuScore;
        }

        public void setIsConsuScore(int isConsuScore) {
            this.isConsuScore = isConsuScore;
        }

        public int getIsCouponKnockConsu() {
            return isCouponKnockConsu;
        }

        public void setIsCouponKnockConsu(int isCouponKnockConsu) {
            this.isCouponKnockConsu = isCouponKnockConsu;
        }

        public Object getIsUsePwd() {
            return isUsePwd;
        }

        public void setIsUsePwd(Object isUsePwd) {
            this.isUsePwd = isUsePwd;
        }

        public Object getMemberName() {
            return memberName;
        }

        public void setMemberName(Object memberName) {
            this.memberName = memberName;
        }

        public Object getMpcResultMsg() {
            return mpcResultMsg;
        }

        public void setMpcResultMsg(Object mpcResultMsg) {
            this.mpcResultMsg = mpcResultMsg;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public List<ConsuDetailListBean> getConsuDetailList() {
            return consuDetailList;
        }

        public void setConsuDetailList(List<ConsuDetailListBean> consuDetailList) {
            this.consuDetailList = consuDetailList;
        }

        public List<?> getCouponList() {
            return couponList;
        }

        public void setCouponList(List<?> couponList) {
            this.couponList = couponList;
        }

        public List<MemberConsuRuleListBean> getMemberConsuRuleList() {
            return memberConsuRuleList;
        }

        public void setMemberConsuRuleList(List<MemberConsuRuleListBean> memberConsuRuleList) {
            this.memberConsuRuleList = memberConsuRuleList;
        }

        public List<String> getRechargeRuleList() {
            return rechargeRuleList;
        }

        public void setRechargeRuleList(List<String> rechargeRuleList) {
            this.rechargeRuleList = rechargeRuleList;
        }

        public static class ConsuDetailListBean {
            /**
             * employeeDiscountLevel : null
             * isEmployee : null
             * memberAmount : 0
             * memberCardListVo : [{"amount":0,"code":"13777575146","codeType":"预存款","couponNum":null,"id":26516430,"lockMemberId":null,"rewardAmount":0,"score":null,"type":3}]
             * memberDiscount : 2
             * memberId : 27496777
             * memberLevelId : 13
             * memberLevelName : 粉丝会员
             * memberRegTime : 2018-10-22 16:04:59
             * memberRewardAmount : 0
             * memberScore : 0
             * memberTypeName : 手机会员
             * name : null
             * relatedCardCode : null
             */

            private Object employeeDiscountLevel;
            private Object isEmployee;
            private int memberAmount;
            private int memberDiscount;
            private int memberId;
            private int memberLevelId;
            private String memberLevelName;
            private String memberRegTime;
            private int memberRewardAmount;
            private int memberScore;
            private String memberTypeName;
            private Object name;
            private Object relatedCardCode;
            private List<MemberCardListVoBean> memberCardListVo;

            public Object getEmployeeDiscountLevel() {
                return employeeDiscountLevel;
            }

            public void setEmployeeDiscountLevel(Object employeeDiscountLevel) {
                this.employeeDiscountLevel = employeeDiscountLevel;
            }

            public Object getIsEmployee() {
                return isEmployee;
            }

            public void setIsEmployee(Object isEmployee) {
                this.isEmployee = isEmployee;
            }

            public int getMemberAmount() {
                return memberAmount;
            }

            public void setMemberAmount(int memberAmount) {
                this.memberAmount = memberAmount;
            }

            public int getMemberDiscount() {
                return memberDiscount;
            }

            public void setMemberDiscount(int memberDiscount) {
                this.memberDiscount = memberDiscount;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getMemberLevelId() {
                return memberLevelId;
            }

            public void setMemberLevelId(int memberLevelId) {
                this.memberLevelId = memberLevelId;
            }

            public String getMemberLevelName() {
                return memberLevelName;
            }

            public void setMemberLevelName(String memberLevelName) {
                this.memberLevelName = memberLevelName;
            }

            public String getMemberRegTime() {
                return memberRegTime;
            }

            public void setMemberRegTime(String memberRegTime) {
                this.memberRegTime = memberRegTime;
            }

            public int getMemberRewardAmount() {
                return memberRewardAmount;
            }

            public void setMemberRewardAmount(int memberRewardAmount) {
                this.memberRewardAmount = memberRewardAmount;
            }

            public int getMemberScore() {
                return memberScore;
            }

            public void setMemberScore(int memberScore) {
                this.memberScore = memberScore;
            }

            public String getMemberTypeName() {
                return memberTypeName;
            }

            public void setMemberTypeName(String memberTypeName) {
                this.memberTypeName = memberTypeName;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public Object getRelatedCardCode() {
                return relatedCardCode;
            }

            public void setRelatedCardCode(Object relatedCardCode) {
                this.relatedCardCode = relatedCardCode;
            }

            public List<MemberCardListVoBean> getMemberCardListVo() {
                return memberCardListVo;
            }

            public void setMemberCardListVo(List<MemberCardListVoBean> memberCardListVo) {
                this.memberCardListVo = memberCardListVo;
            }

            public static class MemberCardListVoBean {
                /**
                 * amount : 0
                 * code : 13777575146
                 * codeType : 预存款
                 * couponNum : null
                 * id : 26516430
                 * lockMemberId : null
                 * rewardAmount : 0
                 * score : null
                 * type : 3
                 */

                private int amount;
                private String code;
                private String codeType;
                private Object couponNum;
                private int id;
                private Object lockMemberId;
                private int rewardAmount;
                private Object score;
                private int type;

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getCodeType() {
                    return codeType;
                }

                public void setCodeType(String codeType) {
                    this.codeType = codeType;
                }

                public Object getCouponNum() {
                    return couponNum;
                }

                public void setCouponNum(Object couponNum) {
                    this.couponNum = couponNum;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public Object getLockMemberId() {
                    return lockMemberId;
                }

                public void setLockMemberId(Object lockMemberId) {
                    this.lockMemberId = lockMemberId;
                }

                public int getRewardAmount() {
                    return rewardAmount;
                }

                public void setRewardAmount(int rewardAmount) {
                    this.rewardAmount = rewardAmount;
                }

                public Object getScore() {
                    return score;
                }

                public void setScore(Object score) {
                    this.score = score;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }
            }
        }

        public static class MemberConsuRuleListBean {
            /**
             * amount : 5
             * awardType : 2
             * giftAmount : 1
             * mostAmount : 100
             * payType : 1,2,3,4,5,6,7,
             * way : 2
             */

            private int amount;
            private int awardType;
            private int giftAmount;
            private int mostAmount;
            private String payType;
            private int way;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getAwardType() {
                return awardType;
            }

            public void setAwardType(int awardType) {
                this.awardType = awardType;
            }

            public int getGiftAmount() {
                return giftAmount;
            }

            public void setGiftAmount(int giftAmount) {
                this.giftAmount = giftAmount;
            }

            public int getMostAmount() {
                return mostAmount;
            }

            public void setMostAmount(int mostAmount) {
                this.mostAmount = mostAmount;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public int getWay() {
                return way;
            }

            public void setWay(int way) {
                this.way = way;
            }
        }
    }

    public static class ResultBean {
        /**
         * code : 0
         * msg : success
         */

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
