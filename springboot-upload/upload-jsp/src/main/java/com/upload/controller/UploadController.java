package com.upload.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 文件（图片）上传
 *
 * @author Administrator
 */
@Controller
public class UploadController {

    /**
     * @author lryepoch
     * @date 2020/12/29 15:27
     * @description 访问http://localhost:8082/uploadPage，跳转到uploadPage.jsp页面
     */
    @RequestMapping("/uploadPage")
    public String uploadPage() {
        return "uploadPage";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(Model model, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {

        //根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();

        System.out.println("-----------------------------");
        System.out.println(System.currentTimeMillis());
        System.out.println(file.getOriginalFilename());
        System.out.println(fileName);

        //获取当前项目的真实路径，然后拼接前面的文件名
        String filePath = request.getServletContext().getRealPath("") + "uploaded" + File.separator + fileName;

        System.out.println(request.getServletContext());
        System.out.println(request.getServletContext().getRealPath(""));
        System.out.println(File.separator);
        System.out.println(filePath);
        System.out.println("-----------------------------");

        //文件所在的目录往往是不存在的，这里需要创建一下目录
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        //把浏览器上传的文件复制到希望的位置
        file.transferTo(destFile);

        model.addAttribute("file", fileName);

        //跳转到show.jsp页面展示上传图片
        return "show";

    }

    @PostMapping("/upload2")
    public String upload2(MultipartFile multipartFile, HttpServletRequest request) {
        FileOutputStream imgOut = null;
        try {
            String path = "/image";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            String fileName = multipartFile.getOriginalFilename();
            String img = id + fileName.substring(fileName.lastIndexOf("."));

            imgOut = new FileOutputStream(new File(dir, img));
            imgOut.write(multipartFile.getBytes());
            return "http://10.2.21.41" + path + img;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                imgOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    private static String UPLOADED_FOLDER = "E://temp//";

    @PostMapping("/upload3")
    public String upload3(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","请选择一个文件！");
            return "请选择一个文件！";
        }

        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER+file.getOriginalFilename());
        Files.write(path, bytes);

        redirectAttributes.addFlashAttribute("message","You successfully uploaded '"+file.getOriginalFilename()+"'");
        return "上传成功";
    }

}
