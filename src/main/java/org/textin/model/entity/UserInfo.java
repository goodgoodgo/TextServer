package org.textin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 10:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {

    /**
     * 记账天数
     */
    private Integer punchDays ;


    /**
     * 打卡天数
     */
    private Integer totalDays ;

    /**
     * 勋章数
     */
    private Integer medals ;

    /**
     * 记账方式选择
     */
    private Integer bookkeepingMethod ;

    /**
     * 模块选择
     */
    private Integer moduleSelection ;

    /**
     * 字体默认设置
     */
    private Integer fontSetting ;

    /**
     * 日历周起始日
     */
    private Integer calendarStartDay ;

    /**
     * 账单月起始日
     */
    private Integer billStartDay ;

    /**
     * 今天是否打卡
     */
    private Integer isChecked ;

}
