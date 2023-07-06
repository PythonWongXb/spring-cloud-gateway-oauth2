package cn.gathub.resourceMenu.controller;

import cn.gathub.resourceMenu.DTO.ProjectListDTO;
import cn.gathub.resourceMenu.VO.MenuVo;
import cn.gathub.resourceMenu.domain.ProjectMenu;
import cn.gathub.resourceMenu.service.imp.ProjectMenuDBImpl;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/menu")
public class Menus {

  @Autowired
  ProjectMenuDBImpl userService;

  @Autowired
  ProjectMenuDBImpl projectMenuDB;

  @PostMapping("/list")
  public MenuVo project(HttpServletRequest request) {
    // 从Header中获取用户信息
    String userStr = request.getHeader("user");
    JSONObject userJsonObject = new JSONObject(userStr);
    System.out.println((userJsonObject));

    List<String> authorities = (List<String>)userJsonObject.get("authorities");
    String authoritiesStr = authorities.stream()
            .map(item -> "`" + item + "`")
            .map(item -> item.replace("`", "'"))
            .collect(Collectors.joining(","));
    List<ProjectMenu> allMenus = projectMenuDB.getBaseMapper().getRoleNames(authoritiesStr);
    MenuVo menuVo = new MenuVo();
    List<ProjectMenu> projectMenus = allMenus.stream()
              .filter(item -> item.getParentCid() == 0)
              .map(item -> {
                  item.setChildren(Menus.getChildren(item, allMenus));
                  return item;
              })
              .sorted(Comparator.comparingInt(ProjectMenu::getSort).reversed())
              .collect(Collectors.toList());
    menuVo.setList(projectMenus);
    menuVo.setTotal(projectMenus.size());
    return menuVo;
  }

  public static List<ProjectMenu> getChildren(ProjectMenu root, List<ProjectMenu> allMenus) {
    return allMenus.stream()
           .filter(item -> item.getParentCid() ==  root.getCatId())
           .map(item -> {
                item.setChildren(Menus.getChildren(item, allMenus));
                return item;
            })
            .sorted(Comparator.comparingInt(ProjectMenu::getSort).reversed())
            .collect(Collectors.toList());
  }
}

