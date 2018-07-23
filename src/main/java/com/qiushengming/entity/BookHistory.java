package com.qiushengming.entity;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Table;
import com.qiushengming.enums.MySqlDefault;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @author qiushengming
 * @date 2018/6/14
 */
@Table(value = "BOOK_HISTORY", resultMapId = "BookHistoryMap")
public class BookHistory
    extends BaseEntity {

    /**
     * 书本编码
     */
    private String bookId;

    /**
     * 借书人
     */
    private String borrower;

    /**
     * 是否归还
     */
    private String isEnable;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    @Column("BOOK_ID")
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    @Column("IS_ENABLE")
    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    @Column(value = "START_DATE", jdbcType = JdbcType.TIMESTAMP, defaultValue = MySqlDefault.CURRENT_TIMESTAMP)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(value = "END_DATE", jdbcType = JdbcType.TIMESTAMP)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
