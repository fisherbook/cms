package net.stackoverflow.cms.service;

import net.stackoverflow.cms.model.dto.MenuDTO;

import java.util.List;

/**
 * 菜单服务接口
 *
 * @author 凉衫薄
 */
public interface MenuService {

    /**
     * 菜单总数
     *
     * @return
     */
    Integer count();

    /**
     * 根据key查询
     *
     * @param keys
     * @return
     */
    List<String> findIdsByKeys(List<String> keys);

    /**
     * 获取菜单键
     *
     * @param id
     * @return
     */
    List<String> findKeysByRoleId(String id);

    /**
     * 查询菜单树
     *
     * @return
     */
    List<MenuDTO> findTree();

    /**
     * 根据用户id查询菜单键
     *
     * @param userId
     * @return
     */
    List<String> findKeysByUserId(String userId);
}
