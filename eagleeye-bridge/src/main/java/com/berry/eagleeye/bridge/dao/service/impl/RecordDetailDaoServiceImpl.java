package com.berry.eagleeye.bridge.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.berry.eagleeye.bridge.dao.entity.RecordDetail;
import com.berry.eagleeye.bridge.dao.mapper.RecordDetailMapper;
import com.berry.eagleeye.bridge.dao.service.IRecordDetailDaoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 处理记录 服务实现类
 * </p>
 *
 * @author HiCooper
 * @since 2020-12-29
 */
@Service
public class RecordDetailDaoServiceImpl extends ServiceImpl<RecordDetailMapper, RecordDetail> implements IRecordDetailDaoService {

    @Resource
    private RecordDetailMapper mapper;
}
