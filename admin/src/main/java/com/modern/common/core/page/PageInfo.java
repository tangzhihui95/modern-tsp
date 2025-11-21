package com.modern.common.core.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/18 23:08
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@ApiModel("前端分页数据")
@Data
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    @ApiModelProperty("总记录数")
    protected long total;
    /**
     * 结果集
     */
    @ApiModelProperty("结果集列表")
    protected List<T> list;
    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private long pageNum;
    /**
     * 每页的数量
     */
    @ApiModelProperty("每页的数量")
    private long pageSize;
    /**
     * 当前页的数量
     */
    @ApiModelProperty("当前页的数量")
    private int size;

    /**
     * 总页数
     */
    @ApiModelProperty("总页数")
    private int pages;

    /**
     * 前一页
     */
    @ApiModelProperty("前一页")
    private int prePage;
    /**
     * 下一页
     */
    @ApiModelProperty("下一页")
    private int nextPage;

    /**
     * 是否为第一页
     */
    @ApiModelProperty("是否为第一页")
    private boolean isFirstPage = false;

    /**
     * 是否为最后一页
     */
    @ApiModelProperty("是否为最后一页")
    private boolean isLastPage = false;

    /**
     * 是否有前一页
     */
    @ApiModelProperty("是否有前一页")
    private boolean hasPreviousPage = false;

    /**
     * 是否有下一页
     */
    @ApiModelProperty("是否有下一页")
    private boolean hasNextPage = false;

    /**
     * 导航页码数
     */
    @ApiModelProperty("导航页码数")
    private long navigatePages;

    /**
     * 所有导航页号
     */
    @ApiModelProperty("所有导航页号")
    private int[] navigatepageNums;

    /**
     * 导航条上的第一页
     */
    @ApiModelProperty("导航条上的第一页")
    private int navigateFirstPage;

    /**
     * 导航条上的最后一页
     */
    @ApiModelProperty("导航条上的最后一页")
    private int navigateLastPage;

    /**
     * 下一个序列值
     */
    @ApiModelProperty(value = ", 分页用,防止重叠数据,默认为0")
    private Long nextSeq = 0L;

    public PageInfo() {

    }

    /**
     * 包装Page对象
     *
     * @param list     page结果
     * @param pageSize 页码数量
     */
    public PageInfo(List<T> list, long pageNum, long pageSize, long total, Long nextSeq) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = list;
        this.size = list.size();
        this.navigatePages = pageSize;
        this.nextSeq = nextSeq;
        this.setTotal(total);
        //计算导航页
        calcNavigatepageNums();
        //计算前后页，第一页，最后一页
        calcPage();
        //判断页面边界
        judgePageBoudary();
    }

    public PageInfo(List<T> list, long pageNum, long pageSize, Long total) {
        List<T> pages = list.stream().skip(pageSize * (pageNum - 1)).limit(pageSize).collect(Collectors.toList());
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = pages;
        this.size = list.size();
        this.navigatePages = pageSize;
        this.setTotal(total);
    }

    public static <T> PageInfo<T> of(List<T> list, long pageNum, long pageSize, long total) {
        return new PageInfo<T>(list, pageNum, pageSize, total, 0L);
    }

    public static <T> PageInfo<T> of(List<T> list, long pageNum, long pageSize, Long total, Long nextSeq) {
        return new PageInfo<T>(list, pageNum, pageSize, total, nextSeq);
    }

    public static <T> PageInfo<T> of(IPage<T> page) {
        return new PageInfo<T>(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal(), 0L);
    }


    /**
     * 内存逻辑分页
     *
     * @param list     分页集合
     * @param pageNum  当前页
     * @param pageSize 每页多少条
     * @param total    总条数
     */
    //TODO DISTRIBUTION FAIL
    public static <T> PageInfo<T> physicalPage(List<T> list, int pageNum, int pageSize, long total) {
        return new PageInfo<T>(list, pageNum, pageSize, total);
    }

    /**
     * 计算导航页
     */
    private void calcNavigatepageNums() {
        //当总页数小于或等于导航页码数时
        if (pages <= navigatePages) {
            navigatepageNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new int[(int) navigatePages];
            int startNum = (int) (pageNum - navigatePages / 2);
            int endNum = (int) (pageNum + navigatePages / 2);

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;
                //最后navigatePages页
                for (int i = (int) (navigatePages - 1); i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
    }

    /**
     * 计算前后页，第一页，最后一页
     */
    private void calcPage() {
        if (navigatepageNums != null && navigatepageNums.length > 0) {
            navigateFirstPage = navigatepageNums[0];
            navigateLastPage = navigatepageNums[navigatepageNums.length - 1];
            if (pageNum > 1) {
                prePage = (int) (pageNum - 1);
            }
            if (pageNum < pages) {
                nextPage = (int) (pageNum + 1);
            }
        }
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages || pages == 0;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < pages;
    }

    public void setTotal(long total) {
        this.total = total;
        if (total == -1) {
            pages = 1;
            return;
        }
        if (pageSize > 0) {
            pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
        } else {
            pages = 0;
        }
        //分页合理化，针对不合理的页码自动处理
        if (pageNum > pages) {
            if (pages != 0) {
                pageNum = pages;
            }
        }
    }

}
