package cinema.demo.controller;

import cinema.demo.entity.Cinema;
import cinema.demo.entity.User;
import cinema.demo.service.UserService;
import cinema.demo.util.CinemaUtil;
import cinema.demo.util.Constant;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@ResponseBody
public class UserController implements Constant {

    @Value("${cinema.path.upload}")
    private String uploadPath;

    @Value("${cinema.path.domain}")
    private String domain;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(User user) {
        System.out.println(user);
        List<User> list = userService.selectUserByName(user.getUsername());
        if(list.size() > 0) {
            return "fail";
        }else {
            Integer rs = userService.insertUser(user);
            if(rs > 0) {
                return "success";
            }else {
                return "fail";
            }
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(String username,String password,HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //System.out.println(username);
        //System.out.println(password);
        //int expiredSeconds = rememberme ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        User user = userService.login(username, password);
        if(user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            String header = user.getHeader();
            byte[] imageBytes = null;
            try (FileInputStream fileInputStream = new FileInputStream(new File(header));) {
                imageBytes = new byte[fileInputStream.available()];
                fileInputStream.read(imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            user.setAvatar(imageBytes);

            if(user.getType() == 0) {
                jsonObject.put("msg", "usersuccess");
                jsonObject.put("code",200);
                jsonObject.put("data", user);
                return jsonObject;
            }else {
                jsonObject.put("msg", "adminsuccess");
                jsonObject.put("code",200);
                jsonObject.put("data", user);
                return jsonObject;
            }
        }
        jsonObject.put("msg", "fail");
        jsonObject.put("code",400);
        return jsonObject;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "退出成功";
    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(User user,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user1 = (User)session.getAttribute("user");
        System.out.println(user);
        int id = user1.getId();
        System.out.println(id);
        Integer count = userService.updateUser(id,user);
        if(count > 0) {
            return "更新用户信息成功";
        }else {
            return "更新用户信息失败";
        }
    }
    @RequestMapping(value = "/modifyUserPwd",method = RequestMethod.POST)
    @ResponseBody
    public String modifyUserPwd(@RequestParam("oldPwd")String oldPwd, @RequestParam("newPwd")String newPwd, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        //System.out.println(user);
        int id = user.getId();
        if(oldPwd == null || "".equals(oldPwd)){
            return "旧密码不能为空";
        }
        if(newPwd == null || "".equals(newPwd)){
            return "新密码不能为空";
        }
        if(oldPwd.equals(newPwd)){
            return "新密码不能与旧密码相同!";
        }
        if(user.getPassword().equals(oldPwd)) {
            user.setPassword(newPwd);
            userService.updateUser(id,user);
            session.removeAttribute("user");
            return "修改密码成功";
        }else {
            return "修改密码失败";
        }
    }


    @RequestMapping(value = "/selectAllUserByPage",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject selectAllUserByPage(@RequestParam(value="page",defaultValue="1")Integer page,@RequestParam(value="limit",defaultValue="10")Integer limit,String keyword) {
        PageInfo<User> info = userService.selectAllUserByPage(page, limit, keyword);
        //System.out.println(info);
        JSONObject obj = new JSONObject();
        obj.put("msg", "用户信息如下");
        obj.put("code", 200);
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        return obj;
    }

    @GetMapping("/selectAllUser")
    @ResponseBody
    public JSONObject selectAllUser() {
        JSONObject obj = new JSONObject();
        List<User> list = userService.selectAllUser();
        obj.put("msg","");
        obj.put("code",0);
        obj.put("count",list.size());
        obj.put("data", list);
        return obj;
    }

    @PostMapping("/selectUserByName")
    @ResponseBody
    public JSONObject selectUsersByName(@RequestParam("username")String username) {
        JSONObject obj = new JSONObject();
        List<User> list = userService.selectUserLikeName(username);
        obj.put("msg","");
        obj.put("code",0);
        obj.put("count",list.size());
        obj.put("data", list);
        return obj;
    }

    @RequestMapping(value = "/updateHeader",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateHeader(MultipartFile file,HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        JSONObject jsonObject = new JSONObject();
        //获取图片后缀（png/jpg/jpeg）
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //生成随机文件名
        fileName = CinemaUtil.getUUID() + suffix;
        //System.out.println(fileName);
        //确定文件存放的路径
        File dest = new File(uploadPath + "/" + fileName);

        //存储文件
        file.transferTo(dest);
        String header = uploadPath+ "/" +fileName;
        System.out.println(header);

        byte[] imageBytes = null;
        try (FileInputStream fileInputStream = new FileInputStream(new File(header));) {
            imageBytes = new byte[fileInputStream.available()];
            fileInputStream.read(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(imageBytes);

        Integer result = userService.updateHeader(user.getId(),header);

        if(result > 0){
            jsonObject.put("code",200);
            jsonObject.put("msg","上传头像成功");
            jsonObject.put("imageBytes",imageBytes);
        }else{
            jsonObject.put("code",400);
            jsonObject.put("msg","上传头像失败");
        }
        return jsonObject;
    }


}
