package com.qiushengming.entity;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Table;

/**
 * @author qiushengming
 * @date 2018/4/22
 */
@Table(value = "BOOK", resultMapId = "Book")
public class Book
    extends BaseEntity {

    /**
     * 名称

     */
    private String name;

    /**
     * 作者
     */
    private String author;

    /**
     * 归属
     */
    private String affiliation;

    /**
     * 资源被谁占有
     */
    private String occupy;

    @Column("NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column("AUTHOR")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column("AFFILIATION")
    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    @Column("OCCUPY")
    public String getOccupy() {
        return occupy;
    }

    public void setOccupy(String occupy) {
        this.occupy = occupy;
    }
}
