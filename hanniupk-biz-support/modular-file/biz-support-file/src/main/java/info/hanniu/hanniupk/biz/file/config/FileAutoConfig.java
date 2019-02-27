package info.hanniu.hanniupk.biz.file.config;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import info.hanniu.hanniupk.biz.file.config.properties.OssProperteis;
import info.hanniu.hanniupk.biz.file.core.storage.FileOperator;
import info.hanniu.hanniupk.biz.file.core.storage.oss.OssFileOperator;
import info.hanniu.hanniupk.kernel.model.constants.ConfigPrefixConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 字典自动配置
 *
 * @author fengshuonan
 * @date 2018-07-27-下午1:57
 */
@Configuration
@ComponentScan("info.hanniu.hanniupk.biz.file")
public class FileAutoConfig {

    /**
     * 阿里云配置
     *
     * @author fengshuonan
     * @Date 2018/6/27 下午2:21
     */
    @Bean
    @ConfigurationProperties(prefix = ConfigPrefixConstants.ALIYUN_OSS)
    public OssProperteis ossProperteis() {
        return new OssProperteis();
    }

    /**
     * oss客户端sdk
     *
     * @author fengshuonan
     * @Date 2018/6/27 下午6:10
     */
    @Bean
    public OSSClient ossClient(OssProperteis ossProperteis) {
        DefaultCredentialProvider defaultCredentialProvider =
                new DefaultCredentialProvider(ossProperteis.getAccessKeyId(), ossProperteis.getAccessKeySecret());
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return new OSSClient(ossProperteis.getEndpoint(), defaultCredentialProvider, clientConfiguration);
    }

    /**
     * 文件操作工具
     *
     * @author fengshuonan
     * @Date 2018/6/27 下午2:21
     */
    @Bean
    public FileOperator fileOperator(OSSClient ossClient, OssProperteis ossProperteis) {
        return new OssFileOperator(ossClient, ossProperteis);
    }

}
