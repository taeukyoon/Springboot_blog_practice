package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


// html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DumnyControllerTest {

    @Autowired //의존성 주입
    private UserRepository userRepository;


    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제 실패하였습니다. 해당 id는 DB에 존재하지 않습니다.";
        }
        return "삭제되었습니다 Id :" + id;
    }

    //save함수는 id를 전달하지 않으면 insert
    //save함수는 id를 전달하면 해당 id의 data가 있으면 update
    //save함수는 id를 전달하면 해당 id의 data가 없으면 insert를 한다.
    //email, password
    @Transactional //함수종료시 자동 commit
    @PutMapping("/dummy/user/{id}")
    public User update(@PathVariable int id, @RequestBody User requestUser) { //Json 데이터를 요청 => Java Object로 변환해서 받아준다.
        System.out.println("id" + id);
        System.out.println("password :" + requestUser.getPassword());
        System.out.println("email :" + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        //userRepository.save(user);
        //더티 체킹
        return user;
    }

    //전체유저조회
    // http://localhost:8000/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list() {
        List<User> user = userRepository.findAll();
        return user;
    }

    //한페이지당 2건의 데이터를 받아본다.
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<User> pagingUsers = userRepository.findAll(pageable);
        List<User> users = pagingUsers.getContent();
        return users;
    }

    // {id} 주소로 파라메터를 전달받는다.
    // http://localhost:8000/dummy/user/4
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        User user = userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사용자는 없습니다."));
        //user 객체 = 자바 오브젝트
        //변환 (웹브라우저가 이해할 수 있는 데이터) -> json
        //스프링부트 MessageConverter가 응답시에 작동
        //java 오브젝트를 리턴하면 MessageConverter Jackson 라이브러리 호출해서 user오브젝트를 Json으로 변환시켜서 브라우저에게 던져준다.
        return user;
    }


    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println("Id" + user.getId());
        System.out.println("username:" + user.getUsername());
        System.out.println("password:" + user.getPassword());
        System.out.println("email:" + user.getEmail());
        System.out.println("createDate:" + user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
