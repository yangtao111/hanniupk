/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.hanniu.hanniupk.core.reqres.response;

import info.hanniu.hanniupk.kernel.model.enums.ResponseStatusEnum;

/**
 * 请求成功的返回
 *
 * @author stylefeng
 * @Date 2018/1/4 22:38
 */
public class SuccessResponseData extends ResponseData {

    public SuccessResponseData() {
        super(true, ResponseStatusEnum.DEFAULT_SUCCESS.getCode(), ResponseStatusEnum.DEFAULT_SUCCESS.getMessage(), null);
    }

    public SuccessResponseData(Object object) {
        super(true, ResponseStatusEnum.DEFAULT_SUCCESS.getCode(), ResponseStatusEnum.DEFAULT_SUCCESS.getMessage(), object);
    }

    public SuccessResponseData(Integer code, String message, Object object) {
        super(true, code, message, object);
    }
}
