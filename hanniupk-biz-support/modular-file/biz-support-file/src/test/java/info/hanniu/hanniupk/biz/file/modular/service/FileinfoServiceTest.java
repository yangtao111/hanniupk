package info.hanniu.hanniupk.biz.file.modular.service;

import base.BaseJunit;
import com.alibaba.fastjson.JSON;
import info.hanniu.hanniupk.biz.file.api.entity.Fileinfo;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 文件相关测试类
 *
 * @author fengshuonan
 * @date 2018-07-25-下午12:24
 */
public class FileinfoServiceTest extends BaseJunit {

    @Resource
    FileinfoService fileinfoService;

    @Test
    public void getFileInfoTest() {
        Fileinfo fileInfo = fileinfoService.getFileInfo(1022770467013726210L);
        System.out.println(JSON.toJSONString(fileInfo));
    }

    @Test
    public void uploadFileAndSaveFileInfoTest() throws FileNotFoundException {
        File file = new File("/Users/zhanglj/Documents/ownSpace/hanniupk-biz-support/biz-support-file/pom.xml");
        FileInputStream fileInputStream = new FileInputStream(file);
        long length = file.length();
        String uuid = fileinfoService.uploadFileAndSaveFileInfo(fileInputStream, "pom.xml", length);
        System.out.println(JSON.toJSONString(uuid));
    }

    @Test
    public void getFileUrlByIdTest() {
        String url = fileinfoService.getFileUrlById(1022770467013726210L);
        System.out.println(url);
    }

    @Test
    public void getFileInfoListTest() {
        List<Fileinfo> fileInfoList = fileinfoService.getFileInfoList(null, null);
        System.out.println(fileInfoList);
    }
}
