import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import test.businessLogic.TestFacadeImplementation;

public class DataAccessTest {

	 static DataAccess sut=new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	 static TestFacadeImplementation testBL=new TestFacadeImplementation();;

	private Event ev;
	
	@Test
	//sut.createQuestion:  The event has one question with a queryText. 
	public void test1() {
		try {
			
			//define paramaters
			String queryText="proba galdera";
			Float betMinimum=new Float(2);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			//configure the state of the system (create object in the dabatase)
			ev = testBL.addEvent(queryText,oneDate );
			sut.createQuestion(ev, queryText, betMinimum);
			
			
			//invoke System Under Test (sut)  
			sut.createQuestion(ev, queryText, betMinimum);
			
			
			//if the program continues fail
		    fail();
		   } catch (QuestionAlreadyExist e) {
			// if the program goes to this point OK  
			fail();
			  // assertTrue(true);
			} finally {
				  //Remove the created objects in the database (cascade removing)   
		          boolean b=testBL.removeEvent(ev);
		           System.out.println("Finally "+b);          
		        }
		   }
	@Test
	//sut.createQuestion:  The event has NOT one question with a queryText. 
	public void test2() {
		try {
			
			//define paramaters
			String queryText="proba galdera";
			Float betMinimum=new Float(2);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			//configure the state of the system (create object in the dabatase)
			ev = testBL.addEvent(queryText,oneDate );			
			
			//invoke System Under Test (sut)  
			Question q=sut.createQuestion(ev, queryText, betMinimum);
			
			
			//verify the results
			assertNotNull(q);
			assertEquals(q.getQuestion(),queryText);
			assertEquals(q.getBetMinimum(),betMinimum,0);
			
			
		   } catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} finally {
				  //Remove the created objects in the database (cascade removing)   
		          boolean b=testBL.removeEvent(ev);
		           System.out.println("Finally "+b);          
		        }
		   }
}
