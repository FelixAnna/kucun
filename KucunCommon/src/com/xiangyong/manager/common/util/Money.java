package com.xiangyong.manager.common.util;

import java.io.Serializable;
import java.math.BigDecimal;

public final class Money implements Serializable {

    /**
     * 金额 单位分
     */
    private BigDecimal amount = new BigDecimal(0);


    public Money() {
    }

    /**
     * 检查金额
     */
    private void checkMoney() {
        if (this.amount == null || this.amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("金额不能小于0");
        }
    }

    /**
     * 构造函数
     *
     * @param amount 单位分
     */
    public Money(long amount) {
        this.amount = new BigDecimal(amount);
        this.checkMoney();
    }

    /**
     * 符点数构造函数，带有小数会四舍五入
     *
     * @param amount 单位分
     */
    public Money(double amount) {
        this.amount = new BigDecimal(amount);
        this.checkMoney();
    }

    /**
     * 符点数构造函数，带有小数会四舍五入
     *
     * @param amount 单位分
     */
    public Money(float amount) {
        this.amount = new BigDecimal(amount);
        this.checkMoney();
    }

    /**
     * 字符串构造函数，带有小数会四舍五入
     *
     * @param amount 单位分
     */
    public Money(String amount) {
        this.amount = new BigDecimal(amount);
        this.checkMoney();
    }

    /**
     * 构造函数
     *
     * @param amount
     */
    public Money(BigDecimal amount) {
        this.amount = amount;
        this.checkMoney();
    }

    /**
     * 获取金额，单位分
     *
     * @return
     */
    public long getAmount() {
        return this.amount.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    }

    /**
     * @param newScale
     * @return
     * @Description: 获取指定精确位数的整数值
     */
    public long getAmount(int newScale) {
        return this.amount.setScale(newScale, BigDecimal.ROUND_HALF_UP).longValue();
    }

    /**
     * 原始金额
     *
     * @return
     */
    public BigDecimal getOriginAmount() {
        return this.amount;
    }

    /**
     * 字符串形式获取金额，单位元，保留两位小数
     *
     * @return
     */
    public String toString() {
        BigDecimal decimal = this.amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal decimalYuan = decimal.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
        return decimalYuan.toString();
    }

    /**
     * 字符串形式获取金额，单位分
     *
     * @return
     */
    public String getAmountInCent() {
        return String.valueOf(this.amount.setScale(0, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 判断金额是否相等
     *
     * @param money
     * @return
     */
    public boolean equals(Money money) {
        return this.amount.equals(money.getAmount());
    }

    /**
     * 金额比较
     *
     * @param money
     * @return
     */
    public int compare(Money money) {
        return this.amount.compareTo(money.getOriginAmount());
    }

    /**
     * 金额相加
     *
     * @param money
     * @return
     */
    public Money add(final Money money) {
        return new Money(this.amount.add(money.getOriginAmount()));
    }

    /**
     * 金额相加
     *
     * @param amount 金额，单位分，有小数会四舍五入
     * @return
     */
    public Money add(final double amount) {
        Money money = new Money(amount);
        return this.add(money);
    }

    /**
     * 金额相加
     *
     * @param amount
     * @return
     */
    public Money add(final long amount) {
        Money money = new Money(amount);
        return this.add(money);
    }

    /**
     * 金额相减，相减小于0抛出异常
     *
     * @param money
     * @return
     */
    public Money subtract(final Money money) {
        return new Money(this.amount.subtract(money.getOriginAmount()));
    }

    /**
     * 金额相减
     *
     * @param amount 单位分，有小数四舍五入
     * @return
     */
    public Money subtract(final String amount) {
        Money money = new Money(amount);
        return this.subtract(money);
    }

    /**
     * 金额相减
     *
     * @param amount 单位分
     * @return
     */
    public Money subtract(final long amount) {
        Money money = new Money(amount);
        return this.subtract(money);
    }

    /**
     * 金额相减
     *
     * @param amount 单位分，有小数四舍五入
     * @return
     */
    public Money subtract(final double amount) {
        Money money = new Money(amount);
        return this.subtract(money);
    }

    /**
     * 金额相乘
     *
     * @param money
     * @return
     */
    public Money multiply(final Money money) {
        return new Money(this.amount.multiply(money.getOriginAmount()));
    }

    /**
     * 金额相乘
     *
     * @param amount
     * @return
     */
    public Money multiply(final long amount) {
        Money money = new Money(amount);
        return this.multiply(money);
    }

    /**
     * 金额相乘
     *
     * @param amount
     * @return
     */
    public Money multiply(final String amount) {
        Money money = new Money(amount);
        return this.multiply(money);
    }

    /**
     * 金额相乘
     *
     * @param amount
     * @return
     */
    public Money multiply(final double amount) {
        Money money = new Money(amount);
        return this.multiply(money);
    }

    /**
     * 金额相除
     *
     * @param money
     * @return
     */
    public Money divide(Money money) {
        if (money.getOriginAmount().equals(BigDecimal.ZERO)) {
            throw new IllegalArgumentException("相除金额不能为0");
        }
        return new Money(this.amount.divide(money.getOriginAmount()));
    }

    /**
     * 金额相除
     *
     * @param amount
     * @return
     */
    public Money divide(long amount) {
        Money money = new Money(amount);
        return this.divide(money);
    }

    /**
     * 金额相除
     *
     * @param amount
     * @return
     */
    public Money divide(String amount) {
        Money money = new Money(amount);
        return this.divide(money);
    }

    /**
     * 金额相除
     *
     * @param amount
     * @return
     */
    public Money divide(double amount) {
        Money money = new Money(amount);
        return this.divide(money);
    }
}
