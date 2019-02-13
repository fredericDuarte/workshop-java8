package java8.ex02;

import java8.data.Account;
import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Exercice 02 - Map
 */
public class Lambda_02_Test {

	// tag::PersonToAccountMapper[]
	interface PersonToAccountMapper {
		Account map(Person p);
	}
	// end::PersonToAccountMapper[]

	// tag::PersonToAccountMapper[]
	interface AccountToFirstnameMapper {
		String map(Account p);
	}
	// end::PersonToAccountMapper[]

	// tag::map[]
	private List<Account> map(List<Person> personList, PersonToAccountMapper mapper) {
		// implémenter la méthode

		List<Account> per = new ArrayList<Account>();

		for (Person p : personList) {

			per.add(mapper.map(p));

		}

		return per;
	}
	// end::map[]

	// tag::map[]
	private List<String> firstName(List<Account> accountList, AccountToFirstnameMapper acc) {
		// implémenter la méthode

		List<String> per = new ArrayList<String>();

		for (Account p : accountList) {

			per.add(p.getOwner().getFirstname());

		}

		return per;
	}
	// end::map[]

	// tag::test_map_person_to_account[]
	@Test
	public void test_map_person_to_account() throws Exception {

		List<Person> personList = Data.buildPersonList(100);

		// transformer la liste de personnes en liste de comptes
		// tous les objets comptes ont un solde à 100 par défaut
		List<Account> result = map(personList, person -> {
			Account a = new Account();
			a.setOwner(person);
			a.setBalance(100);
			return a;
		});

		assertThat(result, hasSize(personList.size()));
		assertThat(result, everyItem(hasProperty("balance", is(100))));
		assertThat(result, everyItem(hasProperty("owner", notNullValue())));
	}
	// end::test_map_person_to_account[]

	// tag::test_map_person_to_firstname[]
	@Test
	public void test_map_person_to_firstname() throws Exception {

		List<Person> personList = Data.buildPersonList(100);

		// transformer la liste de personnes en liste de prénoms
		List<Account> accountList = map(personList, person -> {
			Account a = new Account();
			a.setOwner(person);
			a.setBalance(100);
			return a;
		});

		List<String> result = firstName(accountList, ac -> ac.getOwner().getFirstname());

		// (List<String>) personList

		assertThat(result, hasSize(personList.size()));
		assertThat(result, everyItem(instanceOf(String.class)));
		assertThat(result, everyItem(startsWith("first")));
	}
	// end::test_map_person_to_firstname[]
}
