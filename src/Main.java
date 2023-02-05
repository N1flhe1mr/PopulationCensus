import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        var adultPersons = persons.stream()
                .filter(Person -> Person.getAge() >= 18)
                .count();
        System.out.println("Совершеннолетие население: " + adultPersons + " человек.");

        var conscriptMans = persons.stream()
                .filter(person -> person.getSex().equals(Sex.MAN))
                .filter(person -> person.getAge() <= 27)
                .filter(person -> person.getAge() >= 18)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Мужчины призывного возраста: " + conscriptMans);

        var workingPersons = persons.stream()
                .filter(person -> person.getEducation().equals(Education.HIGHER))
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getSex().equals(Sex.MAN) && person.getAge() <= 70 ||
                        person.getSex().equals(Sex.WOMAN) && person.getAge() <= 65)
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Работоспособное население: " + workingPersons);
    }
}