import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class DiseaseTreeTest {

	@Test
	public void testSize() {
		Network n= new Network(5, .5, 10);
		
		Person p1 = new Person("Eric", n ,10);
		DiseaseTree t = new DiseaseTree(p1);
		assertEquals(1, t.size());
		
		Person p2 = new Person("Jason", n ,10);
		t.add(p1, p2);
		assertEquals(2, t.size());
		
		Person p3 = new Person("Kerry", n ,10);
		t.add(p1, p3);
		assertEquals(3, t.size());
		
		Person p4 = new Person("Doug", n ,10);
		t.add(p2, p4);
		
		Person p5 = new Person("Hui-Fat", n ,10);
		t.add(p3, p5);
		
		Person p6 = new Person("Trump", n ,10);
		t.add(p5, p6);
		
		Person p7 = new Person("Bernie", n ,10);
		
		Person p8 = new Person("Clinton", n ,10);
		t.add(p2, p8);
		
		assertEquals(7, t.size());
		assertEquals(2,t.depthOf(p5));
		assertEquals(-1,t.depthOf(p7));
		assertEquals(3,t.depthOf(p6));
		assertEquals(0,t.depthOf(p1));
		assertEquals(3,t.widthAtDepth(2));
		assertEquals(1,t.widthAtDepth(0));
		assertEquals(0,t.widthAtDepth(4));
		assertEquals(2,t.widthAtDepth(1));
		
		LinkedList<Person> list1=new LinkedList<Person>();
		list1.addFirst(p6);
		list1.addFirst(p5);
		list1.addFirst(p3);
		list1.addFirst(p1);
		assertEquals(list1,t.diseaseRouteTo(p6));
		
		LinkedList<Person> list2=new LinkedList<Person>();
		list2.addFirst(p1);
		assertEquals(list2,t.diseaseRouteTo(p1));
		
		assertEquals(null,t.diseaseRouteTo(p7));
	}

}
