package ru.korolyovegor.LearnUp_Java_Course_hw_20.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Premiere;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Ticket;

import java.util.ArrayList;
import java.util.Date;

@Component
@Aspect
public class Notifier {
    @Around("execution(public * buyTicket(..))")
    public Ticket aroundBuyTicketAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Ticket ticket = (Ticket) proceedingJoinPoint.proceed();
        System.out.println("[куплен билет]: " + ticket);

        return ticket;
    }

    @AfterReturning("execution(public * refundTicket(..))")
    public void afterBuyTicketAdvice(JoinPoint JoinPoint) {
        Premiere premiere = (Premiere) JoinPoint.getArgs()[0];
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
