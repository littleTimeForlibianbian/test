package com.example.lixc.vo.query;

import com.example.lixc.util.ResultJson;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Data
@ToString
public class WorkQuery {

    private int id;

    private int userId;
    //图片id集合
    private String imageIds;
    //作品名称
    private String name;
    //作品描述
    private String content;
    //要查询的标签
    private String label;

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