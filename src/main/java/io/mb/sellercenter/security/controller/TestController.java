package io.mb.sellercenter.security.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping(value = "/api/open1/dcp")
  public String dcp() {
    return "open1";
  }

  @GetMapping(value = "/api/open2/dcp")
  public String dcp2() {
    return "open2";
  }

  @GetMapping(value = "/api/open3/dcp")
  public String dcp3() {
    return "open3";
  }

  @GetMapping(value = "/internal/read/user")
  public String internalRead() {
    return "userRead";
  }

  @GetMapping(value = "/internal/write/user")
  public String internalWrite() {
    return "userWrite";
  }

  @GetMapping(value = "/internal/free/user")
  public String internalFree() {
    return "free";
  }

}
