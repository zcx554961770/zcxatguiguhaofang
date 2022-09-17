package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

public interface UserFollowService extends BaseService<UserFollow> {


    /**
     * 关注房源
     * @param houseId
     * @param request
     * @return
     */
    void follow(Long userId, Long houseId);

    /**
     * 用户是否关注房源
     * @param userId
     * @param houseId
     * @return
     */
    Boolean isFollowed(Long userId, Long houseId);

    PageInfo<UserFollowVo> findListPage(int pageNum, int pageSize, Long userId);
    void cancelFollow(Long id);
}