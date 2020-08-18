package com.example.lixc.vo.query;

import com.example.lixc.common.PageParam;
import com.example.lixc.util.ResultJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/1 11:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class MessageQuery extends PageParam {

    private static final long serialVersionUID = 1650406737303786621L;
    /**
     * 消息id
     */
    private Integer messageId;
    /**
     * 类型  '消息类型:announcement公告/remind提醒/message私信',
     */
    private Integer type;
    /**
     * 动作 '用户动作触发的消息 comment评论，praise点赞，reply回复，recommend推荐 system 系统',
     */
    private String action;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 来源id
     */
    private Integer sourceId;
    /**
     * 来源类型
     */
    private String sourceType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否已读
     */
    private String isRead;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 发送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sendTime;

    public ResultJson checkParams() {
        if (StringUtils.isEmpty(title)) {
            log.info("文章名称为空");
            return ResultJson.buildError("文章名称为空");
        }
        if (StringUtils.isEmpty(content)) {
            log.info("文章内容为空");
            return ResultJson.buildError("文章内容为空");
        }
        if (sendTime == null) {
            log.info("发送时间为空");
            return ResultJson.buildError("发送时间为空");
        }
        return ResultJson.buildSuccess();
    }

}
