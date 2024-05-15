package sample;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sample.Modele.Annee;
import sample.Modele.Genre;
import sample.Modele.Langue;
import sample.Modele.Musique;;


class ModeleTest {
	// note: les testes sont fais avec la mise en commentaire du code modele.java ligne 237
		Modele m;
		Modele.Musique m1;
		Modele.Musique m2;
		Modele.Musique m3;
		Modele.Musique m4;
		Modele.Musique m5;
		Modele.Musique m6;
		Modele.Musique m7;
	@BeforeEach
	void setUp() throws Exception {
		m=new Modele();
		m1 = m.new Musique("ab", "Anonyme", Modele.Genre.pop, Modele.Langue.anglais, Modele.Annee.a80, "25");
		m2 = m.new Musique("abc", "Anonyme", Modele.Genre.pop, Modele.Langue.anglais, Modele.Annee.a00, "25");
		m3 = m.new Musique("cd", "Anonyme", Modele.Genre.rap, Modele.Langue.anglais, Modele.Annee.a00, "50");
		m4 = m.new Musique("ef", "Anon", Modele.Genre.rap_fr, Modele.Langue.français, Modele.Annee.a10, "50");
		m5 = m.new Musique("efg", "Anon", Modele.Genre.rap_fr, Modele.Langue.français, Modele.Annee.a10, "100");
		m6 = m.new Musique("non", "Anne", Modele.Genre.pop, Modele.Langue.français, Modele.Annee.a90, "100");
		m7 = m.new Musique("oui", "Anne", Modele.Genre.pop, Modele.Langue.anglais, Modele.Annee.a90, "100");
		m.ajout(m1);
		m.ajout(m2);
		m.ajout(m3);
		m.ajout(m4);
		m.ajout(m5);
		m.ajout(m6);
		m.ajout(m7);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void rechercheTout() {
		ArrayList test1=new ArrayList();
		test1.add(m1);
		test1.add(m2);
		test1.add(m3);
		test1.add(m4);
		test1.add(m5);
		test1.add(m6);
		test1.add(m7);
		Assert.assertEquals(m.trouveAll(), test1);
		
	}
	
	@Test
	void rechercheSimple() {
		ArrayList test21=new ArrayList();
		test21.add(m1);
		test21.add(m2);
		ArrayList test22=new ArrayList();
		test22.add(m3);
		test22.add(m2);
		ArrayList test23=new ArrayList();
		test23.add(m5);
		Assert.assertEquals(m.trouve("a"), test21);
		Assert.assertEquals(m.trouve("c"), test22);
		Assert.assertEquals(m.trouve("efg"), test23);
	}
	
	@Test
	void recommandation() {
		m.testlisten(m3);
		PriorityQueue test3=m.preference();
		Assert.assertEquals(test3.poll(), m3);
		Assert.assertEquals(test3.poll(), m2);
		
	}
	
	@Test
	void pertinence() {
		Object[] filter= {Genre.pop,Annee.a90,null,null};
		Object[] sort= {null,null,Langue.anglais,null};
		ArrayList base=m.trouveAll();
		PriorityQueue test4=m.pertinence("", filter, sort);
		Assert.assertEquals(test4.poll(), m7);
		Assert.assertEquals(test4.poll(), m6);
		
	}

}
