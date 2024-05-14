import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ModeleTest {
		Modele m;
	@BeforeEach
	void setUp() throws Exception {
		m=new Modele();
		Modele.Musique hee = m.new Musique("test", "Twewy", Modele.Genre.rap, Modele.Langue.anglais, Modele.Annee.a00, 180);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
