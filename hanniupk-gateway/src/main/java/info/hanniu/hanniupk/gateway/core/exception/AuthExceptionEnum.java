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
package info.hanniu.hanniupk.gateway.core.exception;



/**
 * 签名异常
 *
 * @author fengshuonan
 * @date 2018-01-05 14:48
 */
public enum AuthExceptionEnum implements AbstractBaseExceptionEnum{
    /***/
    TOKEN_EMPTY(8001, "登录令牌为空，请检查是否已经登录"),
    TOKEN_ERROR(8002, "token无效"),
    NO_PERMISSION(8003, "没有访问该资源的权限");

    AuthExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

    private String message;
    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
