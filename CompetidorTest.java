package Tp3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Tp3.Competidor.Caracteristica;

class CompetidorTest {

	@Test
	void retadorPierdeDestrezaTest() {
		Competidor alan = new Personaje("Alan","Iz_Exiled", true, 15,23,540,7);
		Competidor jonatan = new Personaje("Jonatan","JoniBR", true, 15,23,546,7);
		
		assertEquals(-1, alan.lucharPorCaracteristica(jonatan, Caracteristica.DESTREZA));
	}
	
	@Test
	void retadorGanaDestrezaTest() {
		Competidor alan = new Personaje("Alan","Iz_Exiled", true, 15,23,549,7);
		Competidor jonatan = new Personaje("Jonatan","JoniBR", true, 15,23,546,7);
		
		assertEquals(1, alan.lucharPorCaracteristica(jonatan, Caracteristica.DESTREZA));
	}
	
	@Test
	void CompetidoresEmpatanDestrezaTest() {
		Competidor alan = new Personaje("Alan","Iz_Exiled", true, 15,23,549,7);
		Competidor jonatan = new Personaje("Jonatan","JoniBR", true, 15,23,546,7);
		
		assertEquals(1, alan.lucharPorCaracteristica(jonatan, Caracteristica.DESTREZA));
	}	
	
}
