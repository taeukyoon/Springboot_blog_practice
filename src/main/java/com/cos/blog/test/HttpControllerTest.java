package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

//사용자 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

    private static final String TAG="HttpController Test";

    @GetMapping("/http/get")
    public String getTest(Member m) {
        return "get 요청:" +m.getId()+"," + m.getUsername()+","+ m.getEmail();
    }

    //인터넷의 브라우저 요청은 get밖에 할 수 없다.
    //json 사용
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m) { //MessageConverter가 작업을 해준다.
        return "post 요청:" +m.getId()+"," + m.getUsername()+","+ m.getEmail();
    }

    @PutMapping("/http/put")
    public String putTest() {
        return "put 요청";
    }

    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
