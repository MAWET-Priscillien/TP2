package ticketmachine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@Before
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : message si erreur, valeur attendue, valeur réelle
		assertEquals("Initialisation incorrecte du prix", PRICE, machine.getPrice());
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals("La balance n'est pas correctement mise à jour", 10 + 20, machine.getBalance()); // Les montants ont été correctement additionnés               
	}
        
        @Test
        // S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
        public void notPrint() {
            machine.insertMoney(10);
            machine.insertMoney(20);
            assertFalse("Le ticket ne doit pas être imprimé si le montant est insuffisant", machine.printTicket());
        }
        
        @Test
        // S4 : on imprime le ticket si le montant inséré est suffisant
        public void printOk(){
            machine.insertMoney(10);
            machine.insertMoney(20);
            machine.insertMoney(20);
            assertTrue("Le ticket doit être imprimé si le montant est suffisant", machine.printTicket());
        }
        
        @Test
        // S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
        public void changeBalancePrint(){
            machine.insertMoney(10);
            machine.insertMoney(20);
            machine.insertMoney(20);
            machine.printTicket();
            assertEquals("La balance n'est pas décrémentée après impression", 0, machine.getBalance());
        }
        
        @Test
        // S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
        public void updateTotal(){
            machine.insertMoney(10);
            machine.insertMoney(20);
            machine.insertMoney(20);
            assertEquals("Le montant collecté est mis à jour avant l'impression du ticket", 0, machine.getTotal());
            machine.printTicket();
            assertEquals("Le montant collecté n'est pas mis à jour après l'impression du ticket", machine.getPrice(), machine.getTotal());
        }
        
        @Test
        //  S7 : refund() rend correctement la monnaie
        public void refundValid(){
            machine.insertMoney(10);
            machine.insertMoney(20);
            assertEquals("refund() ne rend pas correctement la monnaie", 30, machine.refund());
        }
        
        @Test
        //  S8 : refund() remet la balance à zéro
        public void refundReset(){
            machine.insertMoney(10);
            machine.insertMoney(20);
            machine.refund();
            assertEquals("refund() ne remet pas la balance à zéro", 0, machine.getBalance());
        }
        
        @Test (expected = IllegalArgumentException.class)
        // S9 : on ne peut pas insérer un montant négatif
        public void notNegative(){
            machine.insertMoney(-10);
        }
        
        @Test (expected = IllegalArgumentException.class)
        // S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
        public void illegalCreate(){
            TicketMachine machine2 = new TicketMachine(-10);
        }
        

}
