package googy.googyspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GoogyController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-mvc";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "Hi " + name + "!";
    }

    static class Hello {
        private String name;
        private Integer age;

        public String getName(){
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge(){
            return this.age;
        }
        public void setAge(Integer age){
            this.age = age;
        }

    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        Hello hello = new Hello();
        hello.setName(name);
        hello.setAge(age);
        return hello;
    }
}
