package com.yupi.springbootinit.constant;

/**
 * 卡密常量
 */
public interface CardConstant {

    // ========== 卡密类型 ==========

    /**
     * 单次密钥（可解锁 1 份资料）
     */
    Integer TYPE_SINGLE = 1;

    /**
     * 体验密钥（可解锁 2 份资料）
     */
    Integer TYPE_TRIAL = 2;

    /**
     * 标准密钥（可解锁 5 份资料）
     */
    Integer TYPE_STANDARD = 3;

    /**
     * 全通密钥（解锁全部资料）
     */
    Integer TYPE_FULL = 4;

    /**
     * 无限制密钥（9999 次解锁）
     */
    Integer TYPE_UNLIMITED = 5;

    // ========== 卡密状态 ==========

    /**
     * 未激活
     */
    Integer STATUS_INACTIVE = 0;

    /**
     * 已激活（可用）
     */
    Integer STATUS_ACTIVE = 1;

    /**
     * 已使用
     */
    Integer STATUS_USED = 2;

    /**
     * 已过期
     */
    Integer STATUS_EXPIRED = 3;

    // ========== 卡密格式 ==========

    /**
     * 卡密每段长度
     */
    int CARD_SEGMENT_LENGTH = 4;

    /**
     * 卡密段数
     */
    int CARD_SEGMENT_COUNT = 4;

    /**
     * 默认有效期（天）
     */
    int DEFAULT_EXPIRE_DAYS = 365;
}
