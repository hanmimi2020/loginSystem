package com.han.mimi;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @GetMapping(path="/add") // Map ONLY GET REQUESTs.
    public @ResponseBody String addNewUser (@RequestParam String loginId,
    		@RequestParam String password, User user) {
        // @ResponseBody means the returned String is a response, not a view name.

    	//check id and password
    	if("".equals(loginId) || "".equals(password)) {
    		return "IDとパスワードは空白ではいけません！";
    	}
    	// savePassword
    	userService.saveUser(user, loginId, password);

        return loginId + "の登録が完了しました";
    }

    /**
     * loginメソッド、ユーザがメールアドレス及びパスワードを入力し、
     * DBに問い合わせ、ユーザが存在しているかどうかを確認する
     * もしある場合、ログイン成功とする
     * もしいない場合、ユーザが存在しない旨を返す
     * @return
     */
    @GetMapping(path = "/login")
    public @ResponseBody String login(@RequestParam String loginId, @RequestParam String password, Model model) {
    	 List<User>  users = userService.findUser(loginId, password);
    	 log.debug(users.toString());
        if (users.isEmpty()) {
            log.warn("attempting to log in with the non-existed account");
            return "ユーザが存在しません！";
        } else {
            User user = users.get(0);
            if (user.getPassword().equals(password)) {
                model.addAttribute("loginId", user.getLoginId());
                log.warn(user.toString()+ " logged in");
                return "ログイン成功";
            } else {
                model.addAttribute("loginId", "logging failed");
                log.warn(user.toString()+ " failed to log in");
                return "ユーザが存在しないか、パスワードが間違っています！";
            }

        }

    }

    /**
     * アカウント登録画面に遷移
     * @return
     */
    @GetMapping(path="/register") // Map ONLY GET REQUESTs.
    public String register() {
        return "register";
    }
}