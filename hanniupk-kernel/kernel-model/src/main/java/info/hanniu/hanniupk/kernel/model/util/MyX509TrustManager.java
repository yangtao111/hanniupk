package info.hanniu.hanniupk.kernel.model.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.util
 * @ClassName: MyX509TrustManager
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/29 16:28
 * @Version: 1.0
 */
public class MyX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
       // return new X509Certificate[0];
        return null;
    }
}
