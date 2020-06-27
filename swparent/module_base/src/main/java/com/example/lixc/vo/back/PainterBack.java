package com.example.lixc.vo.back;

import com.example.lixc.entity.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 14:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PainterBack extends UserBack {
    private static final long serialVersionUID = 8258451855760241386L;
    //作者标签
    private List<Tag> tags;

    //创作历史/荣誉
    private String history;
}
