package com.zhidianfan.pig.yd.moduler.common.web;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.zhidianfan.pig.common.util.IdUtils;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.config.prop.YdPropertites;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.web.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 文件控制器
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/6
 * @Modified By:
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private YdPropertites ydPropertites;
    @Autowired
    private FileService fileService;

    /**
     * 文件上传接口
     * <p>
     * //不对外开放，生产上不会直接由服务端将文件进行上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Tip fileUpload(@RequestPart Part file, @RequestParam String bucket) throws IOException {
        Tip tip = new SuccessTip(200, "文件上传成功");

        String fileName = file.getSubmittedFileName();
        log.info("上传的文件名为：" + fileName);

        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("上传的后缀名为：" + suffixName);

        String upfileName = IdUtils.getNextId("测试文件上传");

//        file.write(upfileName);

        String upToken = fileService.getQiniuAuth(bucket);


        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());

        UploadManager uploadManager = new UploadManager(cfg);
        Response response = uploadManager.put(file.getInputStream(), upfileName, upToken, null, null);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        System.out.println(putRet.key);
        System.out.println(putRet.hash);


        ((SuccessTip) tip).setContent(JsonUtils.obj2Json(putRet));
        return tip;
    }

    /**
     * 获取图片外链地址
     *
     * @param bucket   存储空间
     * @param fileName 文件名
     * @return
     */
    @GetMapping("/download")
    public Tip getFileUrl(@RequestParam String bucket, @RequestParam String fileName) throws UnsupportedEncodingException {
//        String upToken = fileService.getQiniuAuth(bucket);

        Auth auth = Auth.create(ydPropertites.getQiniu().getAk(), ydPropertites.getQiniu().getSk());

        //绑定的域名
        String domainOfBucket = fileService.getDomainOfBucket(bucket);
        String encodedFileName = URLEncoder.encode(fileName, "utf-8");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        System.out.println("publicUrl\n" + publicUrl);

        String finalUrl = auth.privateDownloadUrl(publicUrl,3600*24*365*50);//生成一个新的带有时效性的临时图片地址  单位：秒，50年

        Tip tip = new SuccessTip(200, "成功获取图片外链地址");
        ((SuccessTip) tip).setContent(finalUrl);
        return tip;
    }

    /**
     * 获取七牛的文件上传凭证
     *
     * @param bucket 存储空间
     * @return
     */
    @RequestMapping(value = "/qiniu/auth", params = "bucket")
    public Tip getQiniuQuth(@RequestParam String bucket) {

        String upToken = fileService.getQiniuAuth(bucket);

        Tip tip = new SuccessTip(200, IdUtils.getNextId(bucket));//返回使用本次凭证上传的文件的id
        ((SuccessTip) tip).setContent(upToken);

        return tip;
    }

}
