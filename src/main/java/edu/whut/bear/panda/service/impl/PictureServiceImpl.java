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
 * @datetime 2022/5/3 20:55
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PixabayMapper pixabayMapper;
    @Autowired
    private BackgroundMapper backgroundMapper;
    @Autowired
    private SpiderUtils spiderUtils;

    @Override
    public Pixabay getFirstPixabay() {
        return pixabayMapper.getFirstPixabay();
    }

    @Override
    public boolean deletePixabayById(Integer id) {
        return pixabayMapper.deletePixabayById(id) == 1;
    }

    @Override
    public int deleteAllPixabay() {
        return pixabayMapper.deleteAllPixabay();
    }

    @Override
    public List<Background> getUserAllBackgrounds(Integer userId) {
        return backgroundMapper.getUserBackgroundsByUserId(userId);
    }

    @Override
    public boolean saveBackground(Background background) {
        return backgroundMapper.saveBackground(background) == 1;
    }

    @Override
    public boolean insertPixabayThoughSpider(String params) {
        return spiderUtils.executeSpider(spiderUtils.getPixabaySpiderRealPath(), params);
    }
}
