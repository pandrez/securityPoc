package io.mb.sellercenter.security.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping(value = "/api/dcp", produces = {"application/json"})
  public String dcp() {
    return "dcp";
  }

  @GetMapping(value = "/internal/cenas", produces = {"application/json"})
  public String internal() {
    return "internal";
  }

}
