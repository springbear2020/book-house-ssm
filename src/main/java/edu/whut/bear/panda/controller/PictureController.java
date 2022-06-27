package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Pixabay;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.pojo.Background;
import edu.whut.bear.panda.service.PictureService;
import edu.whut.bear.panda.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 09:56 Monday
 */
@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @PostMapping("/wallpaper")
    public Response wallpaperUpload(HttpSession session, @RequestParam("wallpaperFile") MultipartFile wallpaperFile) {
        User user = (User) session.getAttribute("user");

        // Save the book file to the local disk
        String realPath = session.getServletContext().getRealPath("/static/wallpaper");
        String fileName = user.getId() + "-" + DateUtils.dateIntoFileName(new Date()) + ".png";
        String coverPath = "static/wallpaper/" + fileName;
        try {
            wallpaperFile.transferTo(new File(realPath + "/" + fileName));
        } catch (IOException e) {
            return Response.danger("服务器图片保存失败，请稍后重试");
        }

        // Save the wallpaper upload record
        Background background = new Background(null, user.getId(), new Date(), coverPath, Background.NORMAL);
        if (!pictureService.saveBackground(background)) {
            return Response.danger("图片记录保存失败，请稍后重试");
        }

        return Response.success("壁纸图片上传成功");
    }

    @GetMapping("/wallpaper")
    public Response getUserBackgrounds() {
        List<Background> backgroundList = pictureService.getAllWallpapers();
        if (backgroundList == null || backgroundList.size() == 0) {
            return Response.info("暂无壁纸数据");
        }
        return Response.success("").put("backgroundList", backgroundList).put("length", backgroundList.size());
    }

    @PutMapping("/pixabay/{condition}/{pages}")
    public Response deleteAllPixabayThenInsertNew(@PathVariable("condition") String condition, @PathVariable("pages") Integer pages) {
        if (condition == null || condition.length() == 0) {
            return Response.info("Pixabay 搜索关键词不能为空");
        }
        if (pages == null || pages <= 0 || pages > Pixabay.MAX_PAGE_NUMBER) {
            return Response.info("Pixabay 请求页数不正确");
        }
        // Delete all existed record of pixabay
        if (pictureService.deleteAllPixabay() < 0) {
            return Response.danger("请求删除所有 Pixabay 数据失败");
        }
        // Get new pixabay record data though python spider
        String params = condition + " " + pages;
        if (!pictureService.insertPixabayThoughSpider(params)) {
            return Response.danger("请求新增 Pixabay 数据失败");
        }
        return Response.success("Pixabay 已新增 " + pages * 20 + " 条记录");
    }

    @GetMapping("/pixabay/first")
    public Response getFirstPixabay() {
        Pixabay pixabay = pictureService.getFirstPixabay();
        if (pixabay == null) {
            return Response.info("Pixabay 图片暂无数据");
        }
        return Response.success("").put("pixabay", pixabay);
    }

    @DeleteMapping("/pixabay/{id}")
    public Response deleteOnePixabay(@PathVariable("id") Integer id) {
        if (id == null) {
            return Response.info("Pixabay ID 不能为空");
        }
        if (!pictureService.deletePixabayById(id)) {
            return Response.danger("Pixabay 图片删除失败");
        }
        return Response.success("成功删除 1 条 Pixabay 记录");
    }
}
