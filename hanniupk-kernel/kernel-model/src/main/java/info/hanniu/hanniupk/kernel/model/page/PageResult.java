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
package info.hanniu.hanniupk.kernel.model.page;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页结果集
 *
 * @author stylefeng
 * @Date 2018/7/22 23:00
 */
@Data
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -4071521319254024213L;

    /**
     * @Author zhanglj
     * @Description 要查找第几页
     * @Date 2018/10/29 2:17 PM
     **/
    private Integer page = 1;
    /**
     * @Author zhanglj
     * @Description 每页显示多少条
     * @Date 2018/10/29 2:17 PM
     **/
    private Integer pageSize = 20;
    /**
     * @Author zhanglj
     * @Description 总页数
     * @Date 2018/10/29 2:18 PM
     **/
    private Integer totalPage = 0;
    /**
     * @Author zhanglj
     * @Description 总记录数
     * @Date 2018/10/29 2:18 PM
     **/
    private Long totalRows = 0L;
    /**
     * @Author zhanglj
     * @Description 结果集
     * @Date 2018/10/29 2:18 PM
     **/
    private List<T> rows;

    public PageResult(Page<T> page) {
        this.setRows(page.getRecords());
        this.setTotalRows(page.getTotal());
        this.setPage(page.getCurrent());
        this.setPageSize(page.getSize());
    }

}
