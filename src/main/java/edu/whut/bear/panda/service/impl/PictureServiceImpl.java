package edu.whut.bear.panda.service.impl;

import edu.whut.bear.panda.dao.BackgroundMapper;
import edu.whut.bear.panda.dao.PixabayMapper;
import edu.whut.bear.panda.pojo.Background;
import edu.whut.bear.panda.pojo.Pixabay;
import edu.whut.bear.panda.service.PictureService;
import edu.whut.bear.panda.util.SpiderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 10:14 Monday
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private BackgroundMapper backgroundMapper;
    @Autowired
    private PixabayMapper pixabayMapper;
    @Autowired
    private SpiderUtils spiderUtils;


    @Override
    public boolean saveBackground(Background background) {
        return backgroundMapper.saveBackground(background) == 1;
    }

    @Override
    public List<Background> getAllWallpapers() {
        return backgroundMapper.getAllBackground();
    }


    @Override
    public int deleteAllPixabay() {
        return pixabayMapper.deleteAllPixabay();
    }

    @Override
    public boolean insertPixabayThoughSpider(String params) {
        return spiderUtils.executeSpider(spiderUtils.getPixabaySpiderRealPath(), params);
    }

    @Override
    public Pixabay getFirstPixabay() {
        return pixabayMapper.getFirstPixabay();
    }

    @Override
    public boolean deletePixabayById(Integer id) {
        return pixabayMapper.deletePixabayById(id) == 1;
    }
}
