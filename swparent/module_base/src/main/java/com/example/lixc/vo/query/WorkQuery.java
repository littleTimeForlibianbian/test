package com.example.lixc.vo.query;

import com.example.lixc.common.PageData;
import com.example.lixc.common.PageParam;
import com.example.lixc.util.ResultJson;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Data
@ToString
public class WorkQuery extends PageParam {

    private int id;

    //z作品所属用户id
    private int userId;
    //图片id集合
    private String imageIds;
    //作品名称
    private String name;
    //要查询的标签
    private String label;
    private Integer status;
    private String userName;

    private String content;
    //点赞数量
    private Integer praiseNum;
    //作品上傳  true   身份認證 false
    private String isNormal;

    //画风标签列表  多个之间以-分割
    private String styleLabelForAdd;
    //品类标签列表。多个之间以-分割
    private String categoryLabelForAdd;

    //    //添加作品时，需要传入的标签数组
//    private String addLabel;
    public ResultJson checkBasicParams() {
        if (StringUtils.isEmpty(imageIds)) {
            return ResultJson.buildError("图片集合为为空");
        }
        if (StringUtils.isEmpty(name)) {
            return ResultJson.buildError("作品名称为空");
        }
        if (StringUtils.isEmpty(styleLabelForAdd)) {
            return ResultJson.buildError("画风标签为空");
        }
        if (StringUtils.isEmpty(categoryLabelForAdd)) {
            return ResultJson.buildError("品类标签为空");
        }
        return ResultJson.buildSuccess();
    }
}