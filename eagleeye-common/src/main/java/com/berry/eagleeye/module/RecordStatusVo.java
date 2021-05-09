package com.berry.eagleeye.module;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2020/12/9
 * fileName：RecordStatusVo
 * Use：
 */
@Data
public class RecordStatusVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long submitId;

    private String recordId;

    private String status;

    private String statusDesc;
}
