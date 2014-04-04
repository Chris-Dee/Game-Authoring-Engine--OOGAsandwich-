package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.GameData;

public class GameDataTest {
	GameData gameData;

	@Before
	public void setUp() {
		gameData = new GameData("");
	}

	@Test
	public void testChildlessClass() {
		TestChildClass childless = new TestChildClass();
		gameData.addObj(childless);
		assertEquals(gameData.toString(),
				"{\"test.TestChildClass\":[{\"aNumber\":5}]}");
	}

	@Test
	public void testClassWithChild() {
		TestParentClass parent = new TestParentClass();
		gameData.addObj(parent);
		assertEquals(gameData.toString(),
				"{\"test.TestParentClass\":[{\"aNumber\":5,\"child\":{\"aNumber\":5}}]}");
	}

	@Test
	public void testIgnoredFields() {
		TestWithTransientClass obj = new TestWithTransientClass();
		gameData.addObj(obj);
		assertEquals(gameData.toString(),
				"{\"test.TestWithTransientClass\":[{\"serializeMe\":5}]}");
	}
}

class TestParentClass {
	private int aNumber;
	private TestChildClass child;

	public TestParentClass() {
		aNumber = 5;
		child = new TestChildClass();
	}
}

class TestChildClass {
	private int aNumber;

	public TestChildClass() {
		aNumber = 5;
	}
}

class TestWithTransientClass {
	private int serializeMe = 5;
	private transient int hideMe = 5;
}
