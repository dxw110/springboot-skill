package com.dcool.springbootactivity.R;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author DCool
 * date 2020-06-05
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, CommonConstants.SUCCESS);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS);
    }


    public static <T> R<T> failed() {
        return restResult(null, CommonConstants.FAIL);
    }


    public static <T> R<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL);
    }


    private static <T> R<T> restResult(T data, String code) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        return apiResult;
    }

}
