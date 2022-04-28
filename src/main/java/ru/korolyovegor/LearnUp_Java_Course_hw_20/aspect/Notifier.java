package ru.korolyovegor.LearnUp_Java_Course_hw_20.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.PremiereEntity;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.TicketEntity;

import java.util.ArrayList;
import java.util.Date;

@Component
@Aspect
public class Notifier {
    @Around("execution(public * buyTicket(..))")
    public TicketEntity aroundBuyTicketAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        TicketEntity ticket = (TicketEntity) proceedingJoinPoint.proceed();
        System.out.println("[куплен билет]: " + ticket);

        return ticket;
    }

    @AfterReturning("execution(public * refundTicket(..))")
    public void afterBuyTicketAdvice(JoinPoint JoinPoint) {
        PremiereEntity premiere = (PremiereEntity) JoinPoint.getArgs()[0];
        System.out.println("[билет возвращен на премьеру]: " + premiere);
    }

    @Around("execution(public * freeSeat())")
    public ArrayList<Integer> aroundFreeSeatAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ArrayList<Integer> freeSeatList = (ArrayList<Integer>) proceedingJoinPoint.proceed();
        Date date = new Date();
        System.out.println("[" + date + ", распределение мест]: " + freeSeatList);
        return freeSeatList;
    }
}
