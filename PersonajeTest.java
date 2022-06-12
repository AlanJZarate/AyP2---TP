package Tp3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonajeTest {

	@Test
	void getNombrePersonajeTest() {
		Personaje alan = new Personaje("Alan", "Iz_Exiled", true, 10, 15, 5, 1);
		assertEquals("Alan", alan.getNombreReal());
	}
}
