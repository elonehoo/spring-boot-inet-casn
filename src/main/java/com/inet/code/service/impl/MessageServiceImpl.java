package com.inet.code.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import com.inet.code.entity.Cipher;
import com.inet.code.entity.Message;
import com.inet.code.entity.Signpull;
import com.inet.code.mapper.MessageMapper;
import com.inet.code.mapper.SignpullMapper;
import com.inet.code.service.CipherService;
import com.inet.code.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inet.code.service.SignpullService;
import com.inet.code.utils.Result;
import com.inet.code.utils.UUIDUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HCY
 * @since 2020-11-07
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private CipherService cipherService;

    @Resource
    private SignpullService signpullService;

    @Resource
    private SignpullMapper signpullMapper;

    /**
     * 进行注册
     * @author HCY
     * @since 2020-11-07
     * @param name 名字
     * @param studentNumber 学号
     * @param telephone 电话号码
     * @param grade 班级
     * @param ua 头文件
     * @param path 路径
     * @return 布尔值
     */
    @Override
    public Result getSave(String name, String studentNumber, String telephone, String grade, String ua,String path) {
        //判断学号是否重复
        if (messageMapper.getByNumber(studentNumber) != null){
            return new Result(500,"Error","学号已经被注册了","注册失败",path);
        }
        //判断电话号码是否正确
        if (!Validator.isMobile(telephone)) {
            return new Result(500,"Error","电话号码不正确","注册失败",path);
        }
        //判断名字是否为中文
        if (!Validator.isChinese(name)){
            return new Result(500,"Error","名字中没中文,请联系管理员","注册失败",path);
        }
        //创建UUID
        String code = UUIDUtils.getCode();
        //创建保存的对象
        Message message = new Message(code,studentNumber,name,telephone,grade,ua);
        //判断保存的是否成功
        if (!this.save(message)){
            this.removeById(code);
            cipherService.removeById(code);
            signpullService.removeById(code);
            return new Result(500,"Error","注册失败","注册失败",path);

        }
        //创建对象
        Cipher cipher = new Cipher(code,studentNumber,studentNumber);
        if (!cipherService.save(cipher)) {
            this.removeById(code);
            cipherService.removeById(code);
            signpullService.removeById(code);
            return new Result(500,"Error","注册失败","注册失败",path);
        }
        //创建对象
        Signpull signpull = new Signpull(code,studentNumber,"false","0","0","0");
        if (!signpullService.save(signpull)) {
            this.removeById(code);
            cipherService.removeById(code);
            signpullService.removeById(code);
            return new Result(500,"Error","注册失败","注册失败",path);
        }
        return new Result(200,"OK","注册成功","注册成功",path);
    }

    /**
     * 登录操作,扫码操作
     * @author HCY
     * @since 2020-11-07
     * @param ua 头请求
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getLogin(String ua, String path) {
        //通过ua,判断是否有这个用户
        if (messageMapper.getByUa(ua) == null) {
            return new Result(
                    404
                    ,"not found"
                    ,"未绑定系统"
                    ,"还未绑定系统,进行系统绑定的登录"
                    ,path);
        }

        //判断是签到还是签退
        Signpull signpull = signpullMapper.getCondition(ua);
        //获取签到人的个人信息
        Message message = messageMapper.getByMessageUa(ua);

        if ("false".equals(signpull.getCheckIS())){
            //进行签到操作
            return this.getSignIn(ua,message.getMemberName(),path);
        }else if ("true".equals(signpull.getCheckIS())){
            //进行签退操作
            return this.getSignOut(ua,message.getMemberName(),path);
        }else {
            return new Result(200,"OK", "联系管理员,同意签到请求,在进行操作哦!",message.getMemberName()+ "同学,请联系管理员",path);
        }
    }

    /**
     * 签到操作
     * @author HCY
     * @since 2020-11-07
     * @param ua 头请求
     * @param path 路径
     * @param name 名字
     * @return Result风格
     */
    @Override
    public Result getSignIn(String ua, String name ,String path) {
        //获取用户的签到状态
        Signpull signpull = signpullMapper.getCondition(ua);
        return getSignInOperation(path, name ,signpull);
    }

    /**
     * 签退操作
     * @author HCY
     * @since 2020-11-07
     * @param ua 头请求
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getSignOut(String ua, String name,String path) {
        //获取用户的签到状态
        Signpull signpull = signpullMapper.getCondition(ua);
        return getSignBackOperation(path, name ,signpull);
    }

    /**
     * 登录操作,并且更新UA
     * @author HCY
     * @since 2020-11-07
     * @param studentNumber 学号
     * @param password 密码
     * @param ua 头请求
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getRegister(String studentNumber, String password, String ua, String path) {
        //判断用户账号密码是否正确
        Message message = messageMapper.getByRegister(studentNumber, password);
        if (message == null){
            return new Result(
                    500
                    ,"Error"
                    ,"登录失败,账号或者密码错误"
                    ,"登录失败"
                    ,path);
        }
        //判断用户的ua是否为空
        try {
            if ("".equals(message.getMemberUA())){
                message.setMemberUA(ua);
                this.updateById(message);
            }
        } catch (Exception e) {
            message.setMemberUA(ua);
            this.updateById(message);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("token",message.getMemberUUID());
        map.put("message","登录成功");

        return new Result(200,"OK","登录成功",map,path);
    }

    /**
     * 按钮的签到
     * @author HCY
     * @since 2020-11-08
     * @param token 令牌
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getCheckInCode(String token, String path) {
        //获取用户的签到状态
        Signpull signpull = signpullService.getById(token);
        Message message = this.getById(token);
        //进行签到状态
        return getSignInOperation(path, message.getMemberName() ,signpull);
    }
    /**
     * 按钮的签退
     * @author HCY
     * @since 2020-11-08
     * @param token 令牌
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getSignOutInCode(String token, String path) {
        //获取用户的签到状态
        Signpull signpull = signpullService.getById(token);
        Message message = this.getById(token);
        return getSignBackOperation(path, message.getMemberName() ,signpull);
    }

    /**
     * 展示所有的用户以及签到时间
     * @author HCY
     * @since 2020-11-08
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getListMessage(String path) {
        return new Result(
                200
                ,"OK"
                ,"获取所有用户谢谢"
                ,messageMapper.getListMessage()
                ,path);
    }

    /**
     * 展示所有是未同意签到的同学
     * @author HCY
     * @since 2020-11-08
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getMaybeMessage(String path) {
        return new Result(
                200
                ,"OK"
                ,"获取成功"
                ,messageMapper.getStaterMessage("maybe")
                ,path);
    }

    /**
     * 展示所有已经在签到的同学
     * @author HCY
     * @since 2020-11-08
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getTrueMessage(String path) {
        return new Result(
                200
                ,"OK"
                ,"获取成功"
                ,messageMapper.getStaterMessage("true")
                ,path);
    }

    /**
     * 通过姓名查询
     * @author HCY
     * @since 2020-11-08
     * @param name 姓名
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getQueryName(String name, String path) {
        return new Result(
                200
                ,"OK"
                ,"搜索查询"
                ,messageMapper.getQueryName("%"+name+"%")
                ,path);
    }

    /**
     * 通过UUID删除用户
     * @author HCY
     * @since 2020-11-08
     * @param uuid uuid
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getRemoveUUID(String uuid, String path) {
        this.removeById(uuid);
        cipherService.removeById(uuid);
        signpullService.removeById(uuid);
        return new Result(200,"OK","删除成功","删除成功",path);
    }

    /**
     * 同意用户的签到请求
     * @author HCY
     * @since 2020-11-08
     * @param studentNumber 学号
     * @return Result风格
     */
    @Override
    public Result getConsent(String studentNumber,String path) {

        return new Result(
                200
                ,"OK"
                ,"同意签到"
                ,signpullMapper.getConsent(studentNumber,"true")
                ,path);
    }

    /**
     * 拒绝用户的请求
     * @param studentNumber 学号
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getRefuse(String studentNumber, String path) {
        return new Result(
                200
                ,"OK"
                ,"拒绝签到"
                ,signpullMapper.getConsent(studentNumber,"false")
                ,path);
    }

    /**
     * 重置用户的签到数据
     * @author HCY
     * @since 2020-11-09
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getReset(String path) {
        if (! getEndOfTheMonth(new Date())) {
            return new Result(403
                    ,"forbidden"
                    ,"禁止的"
                    ,"尚未到达月底，无法点击"
                    ,path);
        }
        return new Result(
                200
                ,"OK"
                ,"重置请求"
                ,signpullMapper.getReset()
                ,path);
    }

    /**
     * 下载文件
     * @author HCY
     * @since 2020-11-09
     * @param response HttpServletResponse
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getDown(HttpServletResponse response, String path) {
        String fileName = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\applyExcel\\"
                + (DateUtil.month(DateUtil.date()) + 1)
                + "月签到统计表.xlsx";
        produceDocuments(fileName);
        //设置产生表格的路径和名字
        String fileURL = "http://47.104.249.85:8080/applyExcel/" + (DateUtil.month(DateUtil.date()) + 1) + "月签到统计表.xlsx";
        return new Result(200,"OK","下载请求",fileURL,path);
    }

    /**
     * 修改密码
     * @author HCY
     * @since 2020-11-13
     * @param token 令牌
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getStorageChgPassword(String token, String oldPassword, String newPassword, String path) {
        //通过token获取用户
        Cipher cipher = cipherService.getById(token);
        //判断用户是否为空
        if (cipher == null){
            return new Result(404,"Not Found","未找到","尚未找到用户",path);
        }
        //判断旧密码是否正确
        if (! cipher.getMemberCipher().equals(oldPassword)){
            return new Result(401,"unlawful","非法的","旧密码错误",path);
        }
        //进行密码的修改
        cipher.setMemberCipher(newPassword);
        //修改密码
        if (cipherService.updateById(cipher)){
            return new Result(200,"OK","修改成功","密码修改成功",path);
        }
        return new Result(500,"Error","修改失败","密码修改失败",path);
    }

    /**
     * 通过Token获取用户的名字
     * @author HCY
     * @since 2020-11-13
     * @param token 令牌
     * @param path 路径
     * @return Result风格
     */
    @Override
    public Result getUserName(String token, String path) {
        Message message = this.getById(token);
        return new Result(200,"OK","获取成功",message.getMemberName(),path);
    }

    private Boolean getEndOfTheMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int getDay = calendar.get(Calendar.DAY_OF_MONTH);
        if(getDay == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 产生文件
     * @author HCY
     * @since 2020-11-09
     * @param fileName 文件完整路径
     */
    private void produceDocuments(String fileName) {
        List<Message> messages = messageMapper.getListMessage();
        //设置Excel的描述文件
        ExportParams exportParams = new ExportParams(
                "签到信息"
                , "签到信息"
                , ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportBigExcel(exportParams,Message.class,messages);
        //输入输出流地址
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            //进行输出流
            workbook.write(fileOutputStream);
            //关流
            fileOutputStream.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 签到操作
     * @author HCY
     * @since 2020-11-08
     * @param path 路径
     * @param signpull 签到状态
     * @return Result风格
     */
    private Result getSignInOperation(String path, String name ,Signpull signpull) {
        //判断是否已经开启了签到
        if ("true".equals(signpull.getCheckIS())){
            return new Result(
                    401
                    ,"unlawful"
                    ,"签到失败"
                    ,name + "同学,您已经在签到中了,请不要重复签到"
                    ,path);
        }else if ("maybe".equals(signpull.getCheckIS())){
            return new Result(
                    401
                    ,"unlawful"
                    ,"签到失败"
                    ,name + "同学,您已经在签到中了,请联系管理员进行同意"
                    ,path);
        }
        //保存开始时间
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String startTime = time.format(formatter);
        //进行签到
        Boolean condition = signpullMapper.updateByCondition(
                signpull.getMemberNumber()
                , startTime
                , "maybe");
        if (condition) {
            return new Result(
                    200
                    ,"OK"
                    ,"签到成功"
                    , name + "同学,您已签到成功"
                    ,path);
        }else {
            return new Result(
                    500
                    ,"Error"
                    ,"签到失败"
                    ,name + "同学,您签到失败"
                    ,path);
        }
    }

    /**
     * 签退操作
     * @author HCY
     * @since 2020-11-08
     * @param path 路径
     * @param signpull 签到状态
     * @param name 名字
     * @return Result风格
     */
    private Result getSignBackOperation(String path, String name ,Signpull signpull) {
        //判断用户的状态
        if ("false".equals(signpull.getCheckIS())){
            return new Result(401,"unlawful",name + "同学,您还没开始签到,无法进行签退","签退失败",path);
        }else if ("maybe".equals(signpull.getCheckIS())){
            return new Result(401,"unlawful",name + "同学,您已经在签到中了,请联系管理员进行同意","签退失败",path);
        }
        //获取现在的时间
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String endTime = time.format(formatter);
        //设置之前的总时间
        long totalTime = Long.valueOf(signpull.getCheckTime());
        //进行时间规划
        SimpleDateFormat simpleFormat = new SimpleDateFormat("HH:mm");
        Date parse = null;
        Date now = null;
        try {
            parse = simpleFormat.parse(signpull.getCheckStart());
            now = simpleFormat.parse(endTime);
        } catch (Exception e) { }
        //获得这两个时间的毫秒值后进行处理(因为我的需求不需要处理时间大小，所以此处没有处理，可以判断一下哪个大就用哪个作为减数。)
        long diff = now.getTime() - parse.getTime();
        //此处用毫秒值除以分钟再除以毫秒既得两个时间相差的分钟数
        long minute = diff/60/1000;
        //计算出所有的签到时间
        totalTime += minute;
        //进行修改
        Boolean condition = signpullMapper.updateSignOut(signpull.getMemberNumber()
                ,endTime
                ,"false"
                ,String.valueOf(totalTime));

        if (condition) {
            return new Result(200,"OK","签退成功",name + "同学,签退成功",path);
        }else {
            return new Result(500,"Error","签退成功",name + "同学,签退成功",path);
        }
    }
}
