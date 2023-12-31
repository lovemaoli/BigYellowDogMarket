package com.TropicalFlavor.controller;

import com.TropicalFlavor.po.*;
import com.TropicalFlavor.service.AdminService;
import com.TropicalFlavor.service.UserService;
import com.TropicalFlavor.tool.StringUtils;
import com.TropicalFlavor.util.MailUtil;
import com.TropicalFlavor.util.Md5Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController
{
    @Resource
    private UserService userService;

    @Resource
    private AdminService adminService;

    //管理员进入管理员后台
    @RequestMapping(value = "/goToAdminStage.do")
    public String goToAdminStage(HttpServletRequest request, Model model)
    {
        User user = (User) request.getSession().getAttribute("marketUser");
        List<User> userList = adminService.ShowUser();

        model.addAttribute("marketUser", user);
        model.addAttribute("userList", userList);

        return "/page/admin";
    }

    @RequestMapping(value = "/goToAdminStage_purchaseRec.do")
    public String goToAdminStage_purchaseRec(HttpServletRequest request, Model model)
    {
        User user = (User) request.getSession().getAttribute("marketUser");
        List<TradeRecord> tradeRecordList = adminService.ShowAllRecord();

        model.addAttribute("marketUser", user);
        model.addAttribute("tradeRecordList", tradeRecordList);

        return "/page/admin_purchaseRec";
    }
//    //根据返回的UID删除
//    @RequestMapping(value = "/deleteThisUser.do")
//    public String deleteUser(HttpServletRequest request, Model model, @RequestParam String UID)
//    {
//        MarketUser user = (MarketUser) request.getSession().getAttribute("marketUser");
//        model.addAttribute("marketUser", user);
//
//        boolean isDeleteSuccess = adminService.DeleteMarketUser(UID);
//
//        return "redirect:/goToAdminStage.do";
//    }
@RequestMapping(value = "/selectAdminStage_purchaseRec")
public String selectAdminStage_purchaseRec(HttpServletRequest request, Model model)
{
    //1000000005	NORM11920192203642   97777878787978 	白夜行
    SelectVO selectVO = new SelectVO();
    String id = request.getParameter("name");
    if (id.matches("\\d+")){
        if (id.length()<=10){
            selectVO.setPid(id);
        }else {
            selectVO.setBuyerId(id);
        }
    }else {
        selectVO.setgName(id);
    }
    User User = (User) request.getSession().getAttribute("marketUser");
    List<TradeRecord> tradeRecordList = adminService.FindRecordById(selectVO);
//
    model.addAttribute("marketUser", User);
    model.addAttribute("tradeRecordList", tradeRecordList);

    return "/page/admin_purchaseRec";
}
    //根据返回的UID注销用户
    @RequestMapping(value = "/deleteThisUser.do")
    public String logOffUser(HttpServletRequest request, Model model, @RequestParam String UID)
    {
        User user = (User) request.getSession().getAttribute("marketUser");
        model.addAttribute("marketUser", user);
        boolean isLogOffSuccess = adminService.logOffMarketUser(UID);
        User targetUser = userService.WhichUser(UID);
        SaleGoods userSaleGoods = userService.ShowSales(targetUser);
        List<Goods> userGoodsList = userSaleGoods.getGoods();
        for(Goods i : userGoodsList)
        {
            userService.GoodsSoldOut(i);
        }

        MailUtil sendMail = new MailUtil(targetUser.getEmail(), 2);
        sendMail.run();
        return "redirect:/goToAdminStage.do";
    }

    //根据返回的UID修改用户
    @RequestMapping(value = "/editThisUser.do")
    public String editUser(HttpServletRequest request, Model model, @RequestParam String UID)
    {
        User user = (User) request.getSession().getAttribute("marketUser");
        model.addAttribute("marketUser", user);

        User targetUser = userService.WhichUser(UID);
        model.addAttribute("targetUser", targetUser);
        model.addAttribute("editUID", UID);

        return "page/AdminEditPerson";
    }

    //接收修改信息
    @RequestMapping(value = "/submitEditInfo.do")
    public String editUserInfo(HttpServletRequest request, Model model,
                             @RequestParam(required = false) String Uname,
                             @RequestParam(required = false) String PhoneNum,
                             @RequestParam(required = false) String Email,
                               @RequestParam String UID)
    {
        User user = (User) request.getSession().getAttribute("marketUser");
        model.addAttribute("marketUser", user);

        User targetUser = userService.WhichUser(UID);


        targetUser.setUname(Uname);
        targetUser.setPhoneNum(PhoneNum);
        targetUser.setEmail(Email);
        adminService.ChangeUserInfo(targetUser);

//        MarketUser tempUser = new MarketUser(Uname, Password, PhoneNum, Email, UID.substring(4));
//        boolean isEditSuccess = adminService.ChangeUserInfo(tempUser);

//        System.out.println(UID);
//        System.out.println(tempUser);
//        System.out.println(isEditSuccess);

        return "redirect:/goToAdminStage.do";
    }

    //管理员新增用户
    @RequestMapping(value = "/goToAddUser.do")
    public String addUser(HttpServletRequest request, Model model)
    {
        User user = (User) request.getSession().getAttribute("marketUser");
        model.addAttribute("marketUser", user);
        return "/page/addNewUser";

    }

    //新增用户提交
    @RequestMapping("/submitNewUser.do")
    public String insertUser(HttpServletRequest request, @RequestParam String sNum, @RequestParam String Uname,
                              @RequestParam String Email, @RequestParam String PhoneNum)
    {
        User user = (User) request.getSession().getAttribute("marketUser");
        if(sNum.isEmpty() || Uname.isEmpty() || Email.isEmpty()
                || PhoneNum.isEmpty())
            return "redirect:/goToAddUser.do";
        String Password=StringUtils.getInstance().getRandomChar();
        User newUser = new User(sNum,Uname, Email, PhoneNum, Password,1);
        boolean isRegisterSuccess = userService.Register(newUser);

        if(isRegisterSuccess)
        {
            request.getSession().setAttribute("marketUser", user);
            MailUtil sendMail = new MailUtil(newUser.getEmail(), Password, 3);
            sendMail.run();
            return "redirect:/goToAdminStage.do";
        }
        else
        {
            return "redirect:/goToAddUser.do";
        }
    }

    //管理员重置密码
    @RequestMapping(value = "/resetPassword.do")
    public String resetPwd(HttpServletRequest request, Model model,
                              @RequestParam String UID)
    {
        User user = (User) request.getSession().getAttribute("marketUser");
        User targetUser = userService.WhichUser(UID);
        String newPassword = StringUtils.getInstance().getRandomChar();

        String pwd = Md5Util.getMD5(newPassword);

        targetUser.setPassword(pwd);
        targetUser.setStatus(1);

        userService.ChangeInfo(targetUser);

        MailUtil sendMail = new MailUtil(targetUser.getEmail(), newPassword, 1);

        sendMail.run();

        return "redirect:/goToAdminStage.do";
    }
}
