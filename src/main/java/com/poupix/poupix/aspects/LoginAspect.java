package com.poupix.poupix.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

@Aspect
@Component
public class LoginAspect {

    //PointCut para todos os metodos publicos em controller
    @Pointcut("execution(public * com.poupix.poupix.controller.*.*(..))")
    public void contollerMethod(){ }

    //Advice Around: Loga antes e depois incluindo usuário autendicado
    @Around("contollerMethod()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.isAuthenticated()) ? auth.getName() : "anonimo";

        System.out.println("Iniciando metodo: " + joinPoint.getSignature().getName() + "| Usuário: " + username + "Args: " + joinPoint.getArgs());

        Object result = joinPoint.proceed();

        System.out.println("Metodo concluido: "+ joinPoint.getSignature().getName() + "| Resultado: "+ result); return result;
    }
}
