package com.example.lixc.service.impl;

import com.example.lixc.entity.Code;
import com.example.lixc.mapper.CodeMapper;
import com.example.lixc.service.CodeService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.SysConfigUtil;
import com.example.lixc.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 14:53
 */
@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeMapper codeMapper;


    @Value("${sw.code.length}")
    private int length;

    /**
     * 生成邀请码
     * 暂时不加缓存，以后有需要再加
     *
     * @return
     */
    public ResultJson genInvitationCode() {
        String word = ToolsUtil.generateWord(0, length);
        Code code = new Code();
        code.setCode(word);
        code.setUsedNum(0);
        code.setCreateBy(SysConfigUtil.getLoginUserId());
        code.setCreateTime(new Date());
        //写库
        codeMapper.insertSelective(code);
        return ResultJson.buildSuccess(word);
    }

    public int selectCountByCode(String code) {
        return codeMapper.selectCountByCode(code);
    }
}
