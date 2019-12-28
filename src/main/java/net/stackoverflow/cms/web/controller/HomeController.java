package net.stackoverflow.cms.web.controller;

import lombok.extern.slf4j.Slf4j;
import net.stackoverflow.cms.common.BaseController;
import net.stackoverflow.cms.common.Result;
import net.stackoverflow.cms.model.vo.UserAuthorityVO;
import net.stackoverflow.cms.security.CmsUserDetails;
import net.stackoverflow.cms.util.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取菜单权限
 *
 * @author 凉衫薄
 */
@RestController
@RequestMapping(value = "/home")
@Slf4j
public class HomeController extends BaseController {

    /**
     * 获取菜单权限
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/menu")
    public ResponseEntity menu(HttpServletRequest request) {
        Result result = new Result();
        try {
            HttpSession session = request.getSession();
            CmsUserDetails userDetails = (CmsUserDetails) session.getAttribute("user");
            UserAuthorityVO userAuthorityVO = new UserAuthorityVO();
            userAuthorityVO.setUsername(userDetails.getUsername());
            userAuthorityVO.setEmail(userDetails.getEmail());
            userAuthorityVO.setTelephone(userDetails.getTelephone());
            List<String> roles = new ArrayList<>();
            List<String> permissions = new ArrayList<>();
            for (GrantedAuthority authority : userDetails.getAuthorities()) {
                String str = authority.getAuthority();
                if (str.startsWith("ROLE_")) {
                    roles.add(str.substring(str.indexOf("_") + 1));
                } else {
                    permissions.add(str);
                }
            }
            userAuthorityVO.setRoles(roles);
            userAuthorityVO.setPermissions(permissions);

            result.setStatus(Result.Status.SUCCESS);
            result.setMessage("success");
            result.setData(userAuthorityVO);
            log.info(JsonUtils.bean2json(result));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setStatus(Result.Status.FAILURE);
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}