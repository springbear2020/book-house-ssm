package edu.whut.bear.panda.service.impl;

import edu.whut.bear.panda.dao.PixabayMapper;
import edu.whut.bear.panda.pojo.Pixabay;
import edu.whut.bear.panda.service.PictureService;
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

    @Override
    public List<Pixabay> getPixabayByPositionAndOffset(Integer start, Integer offset) {
        // Because the index value begin with zero not one, so let start - 1
        start -= 1;
        return pixabayMapper.getPixabayByPositionAndOffset(start, offset);
    }

    @Override
    public boolean deletePixabayById(Integer id) {
        return pixabayMapper.deletePixabayById(id) == 1;
    }

    @Override
    public boolean deleteAllPixabay() {
        return pixabayMapper.deleteAllPixabay() >= 0;
    }
}
