package cn.com.cgh.auth.component;


import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class KeyStoreUtil {

    /**
     * 从JKS文件中提取密钥对
     *
     * @param keystorePath     JKS文件路径
     * @param keystorePassword JKS文件密码
     * @param keyAlias         密钥对的别名
     * @return 返回包含私钥和证书的密钥对
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     */
    public KeyStore.Entry getKeyEntry(String keystorePath, String keystorePassword, String keyAlias) throws IOException, KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, CertificateException, CertificateException {
        // 加载密钥库
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());

        // 获取密钥对
        KeyStore.Entry keyEntry = keyStore.getEntry(keyAlias, null);

        if (keyEntry instanceof KeyStore.PrivateKeyEntry) {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyEntry;
            return privateKeyEntry;
        } else {
            throw new RuntimeException("Invalid key entry type");
        }
    }

    /**
     * 从JKS文件中提取私钥
     *
     * @param keystorePath     JKS文件路径
     * @param keystorePassword JKS文件密码
     * @param keyAlias         密钥对的别名
     * @return 返回私钥
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     */
    public PrivateKey getPrivateKey(String keystorePath, String keystorePassword, String keyAlias) throws IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException, CertificateException {
        KeyStore.Entry keyEntry = getKeyEntry(keystorePath, keystorePassword, keyAlias);
        if (keyEntry instanceof KeyStore.PrivateKeyEntry) {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyEntry;
            return privateKeyEntry.getPrivateKey();
        } else {
            throw new RuntimeException("Invalid key entry type");
        }
    }

    /**
     * 从JKS文件中提取证书
     *
     * @param keystorePath     JKS文件路径
     * @param keystorePassword JKS文件密码
     * @param keyAlias         密钥对的别名
     * @return 返回证书
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     */
    public Certificate getCertificate(String keystorePath, String keystorePassword, String keyAlias) throws IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException, CertificateException {
        KeyStore.Entry keyEntry = getKeyEntry(keystorePath, keystorePassword, keyAlias);
        if (keyEntry instanceof KeyStore.PrivateKeyEntry) {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyEntry;
            return privateKeyEntry.getCertificate();
        } else {
            throw new RuntimeException("Invalid key entry type");
        }
    }
}