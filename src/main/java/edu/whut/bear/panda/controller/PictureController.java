package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.*;
import edu.whut.bear.panda.service.PictureService;
import edu.whut.bear.panda.service.TransferService;
import edu.whut.bear.panda.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 20:56
 */
@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;
    @Autowired
    private TransferService transferService;

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
        return Response.success("");
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

    @GetMapping("/wallpaper/all")
    public Response getUserBackgrounds() {
        List<Background> backgroundList = pictureService.getAllWallpapers();
        if (backgroundList == null || backgroundList.size() == 0) {
            return Response.info("暂无壁纸数据");
        }
        return Response.success("").put("backgroundList", backgroundList).put("length", backgroundList.size());
    }

    @DeleteMapping("/background/{id}")
    public Response deleteOneBackgroundById(@PathVariable("id") Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.info("请先登录您的账号");
        }
        Background background = pictureService.getBackgroundById(id);
        if (background == null) {
            return Response.danger("无此条背景图片记录信息");
        }
        // Update the background record status
        if (!pictureService.updateBackgroundStatus(id, Background.STATUS_ABNORMAL)) {
            return Response.danger("更新图片记录失败");
        }

        // Delete the file in the Qiniu cloud
        String imgUrl = background.getUrl();
        String key = StringUtils.getContentAfterDomain(imgUrl);
        if (!transferService.deleteFileByKey(key, Upload.TYPE_IMAGE)) {
            return Response.danger("背景图片文件删除失败");
        }

        return Response.success("");
    }
}