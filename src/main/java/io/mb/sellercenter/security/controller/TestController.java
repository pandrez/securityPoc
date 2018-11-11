package io.mb.sellercenter.security.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping(value = "/api/dcp")
  public String dcp() {
    return "dcp";
  }

  @GetMapping(value = "/internal/cenas")
  public String internal() {
    return "internal";
  }

  @GetMapping(value = "/api2/dcp")
  public String dcp2() {
    return "dcp2";
  }

  @GetMapping(value = "/api3/dcp")
  public String dcp3() {
    return "dcp3";
  }

  @GetMapping(value = "/api4/dcp")
  public String dcp4() {
    return "dcp4";
  }

}
