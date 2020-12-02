package com.sunlu.chengxin.aop;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * aop的使用测试
 */
@Aspect
@Component
public class PermissionAspect {

    /**
     * 切入点
     * 切入点为包路径下的：execution(public * org.ylc.note.aop.controller..*(..))：
     * org.ylc.note.aop.Controller包下任意类任意返回值的 public 的方法
     * <p>
     * 切入点为注解的： @annotation(VisitPermission)
     * 存在 VisitPermission 注解的方法
     */
//    @Pointcut("execution(public * com.sunlu.chengxin.controller..*(..))")

    @Pointcut("@annotation(VisitPermission)")
    private void permission() {

    }

    /**
     * 目标方法调用之前执行
     */
    @Before("permission()")
    public void doBefore() {
        System.out.println("================== step 2: before ==================");
    }


    /**
     * 目标方法调用之后执行
     */
    @After("permission()")
    public void doAfter() {
        System.out.println("================== step 4: after ==================");
    }

    /**
     * 环绕
     * 会将目标方法封装起来
     * 具体验证业务数据
     */
    @Resource
    KafkaTemplate<String,Object> kafkaTemplate;

    @Around("permission()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("================== step 1: around ==================");
        long startTime = System.currentTimeMillis();
        /*
         * 获取当前http请求中的token
         * 解析token :
         * 1、token是否存在
         * 2、token格式是否正确
         * 3、token是否已过期（解析信息或者redis中是否存在）
         * */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取请求参数
        String params = request.getParameter("params");

        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        VisitPermission visitPermission = method.getAnnotation(VisitPermission.class);
        String value = visitPermission.value();

        System.out.println(value);

        // 执行具体方法
        Object result = proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();

        /*
         * 记录相关执行结果
         * 可以存入MongoDB 后期做数据分析
         * */

        //通过kafka发送，存入日志
        //简单的kafka生产者
        kafkaTemplate.send("test",params);

        // 打印请求 url
        System.out.println("URL            : " + request.getRequestURL().toString());
        // 打印 Http method
        System.out.println("HTTP Method    : " + request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        System.out.println("controller     : " + proceedingJoinPoint.getSignature().getDeclaringTypeName());
        // 调用方法
        System.out.println("Method         : " + proceedingJoinPoint.getSignature().getName());
        // 执行耗时
        System.out.println("cost-time      : " + (endTime - startTime) + " ms");

        return result;
    }
}