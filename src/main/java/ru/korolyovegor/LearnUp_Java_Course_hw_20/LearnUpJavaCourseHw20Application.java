package ru.korolyovegor.LearnUp_Java_Course_hw_20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Premiere;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.service.PremiereService;

import java.util.UUID;

@SpringBootApplication
public class LearnUpJavaCourseHw20Application {

	public static void main(String[] args) {
		SpringApplication.run(LearnUpJavaCourseHw20Application.class, args);
		PremiereService ps = new PremiereService();

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

		ps.delete(p.getId());
		ps.read();
	}

}
