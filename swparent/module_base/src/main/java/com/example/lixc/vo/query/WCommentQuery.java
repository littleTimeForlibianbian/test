package com.example.lixc.vo.query;

import com.example.lixc.util.ResultJson;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 22:30
 */
@Data
public class WCommentQuery {
    //作品id
    private Integer workId;
    //评论内容
    private String content;
    //评论等级  评论作品为一级评论， 评论评论为二级评论  默认为1级
    private Integer commentLevel = 1;
    //父级id  用于保存被评论的id
    private Integer parentId = 0;

    //类型
    private String targetType;

    public ResultJson checkParams() {
        if (workId < 0) {
            return ResultJson.buildError("作品id为空");
        }
        if (StringUtils.isEmpty(content)) {
            return ResultJson.buildError("评论内容为空");
        }
        if (commentLevel == 2 && parentId == 0) {
            return ResultJson.buildError("评论不合法");
        }
        return ResultJson.buildSuccess();
    }
}
