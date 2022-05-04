package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Pixabay;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.service.PictureService;
import edu.whut.bear.panda.util.PropertyUtils;
import edu.whut.bear.panda.util.SpiderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 20:56
 */
@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;
    @Autowired
    private PropertyUtils propertyUtils;

    @GetMapping("/pixabay")
    public Response getFirstPixabay() {
        Pixabay pixabay = pictureService.getFirstPixabay();
        if (pixabay == null) {
            return Response.info("Pixabay 图片暂无数据", null);
        }
        return Response.success("", pixabay);
    }

    @DeleteMapping("/pixabay/{id}")
    public Response deleteOnePixabay(@PathVariable("id") Integer id) {
        if (id == null) {
            return Response.warning("Pixabay ID 不能为空", null);
        }
        if (!pictureService.deletePixabayById(id)) {
            return Response.danger("Pixabay 图片删除失败", null);
        }
        return Response.success("", null);
    }

    @PutMapping("/pixabay/{condition}/{pages}")
    public Response deleteAllPixabayThenInsertNew(@PathVariable("condition") String condition, @PathVariable("pages") Integer pages) {
        if (condition == null || condition.length() == 0) {
            return Response.info("Pixabay 检索条件不能为空", null);
        }
        if (pages == null || pages <= 0 || pages > Pixabay.MAX_PAGE_NUMBER) {
            return Response.info("Pixabay 请求页数不正确", null);
        }
        // Delete all existed record of pixabay
        if (!pictureService.deleteAllPixabay()) {
            return Response.danger("请求删除所有 Pixabay 数据失败", null);
        }
        String pixabaySpiderRealPath = propertyUtils.getPixabaySpiderRealPath();
        if (pixabaySpiderRealPath == null) {
            return Response.danger("请求读取爬虫文件路径失败", null);
        }
        if (!SpiderUtils.executeSpider(pixabaySpiderRealPath, condition + " " + pages)) {
            return Response.danger("请求获取 Pixabay 数据失败", null);
        }
        return Response.success("Pixabay 已新增 " + pages * 25 + " 条记录", null);
    }
}
