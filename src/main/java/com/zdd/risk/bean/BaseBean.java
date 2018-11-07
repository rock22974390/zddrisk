package com.zdd.risk.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 使用apache.commons.lang3.builder重写了三个方法
 * @author 租无忧科技有限公司
 * @date 2018-11-01.
 */
public class BaseBean {

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * 可以详细显示对象的各个属性值
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}