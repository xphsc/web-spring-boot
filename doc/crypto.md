
#### 使用详情
1.在 springboot 默认的yml或者Properties文件中配置
 yml
```
web:
   crypto:
    enabled: true
    encoding: utf-8
    decrypt:
      key: abcdefgabcdefg12

```
 1.1 在controller 类或者方法上加上Encrypt（加密）Decrypt（解密）
    以下例子仅供参考
~~~~
    @RestController
    @RequestMapping("/")
    @Encrypt
    public class TestModelController {}
 /**
     * js 加密 example
     * var key = CryptoJS.enc.Utf8.parse(key);
     * var srcs = CryptoJS.enc.Utf8.parse(content);
     * var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
     */
/**
  *js  解密 example
  var key = CryptoJS.enc.Utf8.parse(key);
  var decrypt = CryptoJS.AES.decrypt(content, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
  return CryptoJS.enc.Utf8.stringify(decrypt).toString();
  */
~~~~
Properties
```
