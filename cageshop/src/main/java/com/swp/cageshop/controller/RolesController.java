package com.swp.cageshop.controller;

import com.swp.cageshop.entity.Roles;
import com.swp.cageshop.service.rolesService.IRolesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cageshop")
public class RolesController {

  @Autowired
  private IRolesService iRolesService;

  @GetMapping("/role/haha")
  public String test(){
    return "djt me may";
  }

  @PostMapping("/role/add")
  public Roles addRoles(@RequestBody Roles roles){
    return iRolesService.addRoles(roles);
  }

  @GetMapping("/role/list")
  public List<Roles> listMarketings(){
    return iRolesService.listRoles();
  }

  @DeleteMapping("/role/delete/{id}")
  public boolean deleteRoles(@PathVariable long id){
    return iRolesService.deleteRoles(id);
  }

}