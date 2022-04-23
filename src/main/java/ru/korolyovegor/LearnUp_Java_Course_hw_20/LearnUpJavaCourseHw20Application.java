package ru.korolyovegor.LearnUp_Java_Course_hw_20;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Premiere;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Ticket;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.service.PremiereService;

import java.util.UUID;

@SpringBootApplication
public class LearnUpJavaCourseHw20Application implements CommandLineRunner {

	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(LearnUpJavaCourseHw20Application.class, args);
	}

	@Override
	public void run(String... args) {
		PremiereService ps = context.getBean(PremiereService.class);

		System.out.println("----------------------------");
		ps.read();
		System.out.println("----------------------------");

		ps.insert(Premiere.builder()
				.id(UUID.randomUUID())
				.name("abc")
				.description("qqqwwweeerrr tttyyy")
				.ageCategory(16)
				.quantityOfSeats(350)
				.build());
		ps.read();
		System.out.println("----------------------------");

		ps.insert(Premiere.builder()
				.id(UUID.randomUUID())
				.name("def")
				.description("31415926535")
				.ageCategory(6)
				.quantityOfSeats(800)
				.build());
		Premiere p = Premiere.builder()
				.id(UUID.randomUUID())
				.name("ghi")
				.description("27182818284")
				.ageCategory(18)
				.quantityOfSeats(1500)
				.build();
		ps.insert(p);
		ps.read();
		System.out.println("----------------------------");

		ps.readById(p.getId());
		System.out.println("----------------------------");

		p.setName("ASDF");
		ps.update(p);
		ps.readById(p.getId());
		System.out.println("----------------------------");

//		ps.delete(p.getId());
//		ps.read();

		Ticket t = ps.buyTicket(p);
		System.out.println("----------------------------");

		ps.refundTicket(p);
		System.out.println("----------------------------");

		ps.buyTicket(p);
		System.out.println("----------------------------");

		System.out.println(ps.freeSeat());
	}

}
