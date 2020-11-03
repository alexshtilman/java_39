package telran.util.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.*;

class IndexedListTest {
	private static final String TEST_CONFIG_DATA = "testConfig.txt";
	int[] arrayInt = { 1, 2, 3, 4, 5, 6, 7, 8 };
	String[] arrayString = { "i", "like", "java", "more", "then", "i", "like", "to", "sleep" };

	IndexedList<Integer> listInt;
	IndexedList<String> listStr;

	int[] patternInt = { 1, 8, 5 };
	String[] patternString = { "i", "more", "sleep" };
	IndexedList<Integer> patternListInt;
	IndexedList<String> patternListStr;

	int[] antiPatternInt = { 33, -1, 54 };
	String[] antiPatternString = { "dance", "all", "night" };
	IndexedList<Integer> antiPatternListInt;
	IndexedList<String> antiPatternListStr;

	static String nameOfClass;
	@BeforeAll
	static void readConfigFromFile() {
		try  {
			nameOfClass = Files.readString(Paths.get(TEST_CONFIG_DATA), StandardCharsets.US_ASCII);
			System.out.println("Config file found, testing class: " + nameOfClass);
		} catch (Exception e) {
			try {
				 Files.writeString(Paths.get(TEST_CONFIG_DATA), "telran.util.Array", StandardCharsets.US_ASCII);
				nameOfClass = "telran.util.Array";
				System.out.println("Config file not found, testing default class: " + nameOfClass);
			} catch (IOException e1) {
				System.out.println("FileNotFoundException: " + e1.getMessage());
				fail();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setup() {

		try {
			listInt = (IndexedList<Integer>) Class.forName(nameOfClass).getConstructor().newInstance();
			listStr = (IndexedList<String>) Class.forName(nameOfClass).getConstructor().newInstance();

			patternListInt = (IndexedList<Integer>) Class.forName(nameOfClass).getConstructor().newInstance();
			patternListStr = (IndexedList<String>) Class.forName(nameOfClass).getConstructor().newInstance();
			antiPatternListInt = (IndexedList<Integer>) Class.forName(nameOfClass).getConstructor().newInstance();
			antiPatternListStr = (IndexedList<String>) Class.forName(nameOfClass).getConstructor().newInstance();
		} catch (Exception e) {
			e.getStackTrace();
			fail();
		} 

		for (int i = 0; i < arrayInt.length; i++) {
			listInt.add(arrayInt[i]);
		}
		for (int i = 0; i < arrayString.length; i++) {
			listStr.add(arrayString[i]);
		}
		for (int i = 0; i < patternInt.length; i++) {
			patternListInt.add(patternInt[i]);
		}
		for (int i = 0; i < patternString.length; i++) {
			patternListStr.add(patternString[i]);
		}

		for (int i = 0; i < antiPatternInt.length; i++) {
			antiPatternListInt.add(antiPatternInt[i]);
		}
		for (int i = 0; i < antiPatternString.length; i++) {
			antiPatternListStr.add(antiPatternString[i]);
		}
	}

	@Test
	void testAddGet() {
		assertEquals(arrayInt.length, listInt.size());
		assertEquals(arrayString.length, listStr.size());
		for (int i = 0; i < arrayInt.length; i++) {
			assertEquals(arrayInt[i], listInt.get(i));
		}
		for (int i = 0; i < arrayString.length; i++) {
			assertEquals(arrayString[i], listStr.get(i));
		}
	}

	@Test
	void testAddByIndex() {
		int[] expectedString = { 25, 1, 35, 2, 3, 4, 5, 6, 7, 45, 8 };
		listInt.add(0, 25);
		listInt.add(2, 35);
		listInt.add(9, 45);
		for (int i = 0; i < expectedString.length; i++) {
			assertEquals(expectedString[i], listInt.get(i));
		}
		listInt.add(-1, -1);
	}

	@Test
	void testRemoveByIndex() {
		int[] expectedString = { 2, 3, 5, 6, 7 };
		assertEquals(1, listInt.remove(0));
		assertEquals(8, listInt.remove(6));
		assertEquals(4, listInt.remove(2));
		for (int i = 0; i < expectedString.length; i++) {
			assertEquals(expectedString[i], listInt.get(i));
		}
		assertEquals(null, listInt.remove(-1));
	}

	@Test
	void testFirstIndexOf() {
		assertEquals(0, listStr.indexOf("i"));
		assertEquals(3, listStr.indexOf("more"));
		assertEquals(8, listStr.indexOf("sleep"));
		assertEquals(-1, listStr.indexOf("not found"));
	}

	@Test
	void testLastIndexOf() {
		assertEquals(5, listStr.lastIndexOf("i"));
		assertEquals(2, listStr.lastIndexOf("java"));
		assertEquals(8, listStr.lastIndexOf("sleep"));
		assertEquals(-1, listStr.lastIndexOf("not found"));
	}

	@Test
	void testReverse() {
		String[] expectedString = { "sleep", "to", "like", "i", "then", "more", "java", "like", "i" };
		Integer[] expectedInt = { 8, 7, 6, 5, 4, 3, 2, 1 };
		listStr.reverse();
		listInt.reverse();
		assertEquals(expectedString.length, listStr.size());
		assertEquals(expectedInt.length, listInt.size());
		for (int i = 0; i < expectedString.length; i++) {
			assertEquals(expectedString[i], listStr.get(i));
		}
		for (int i = 0; i < expectedInt.length; i++) {
			assertEquals(expectedInt[i], listInt.get(i));
		}
	}

	@Test
	void testRemoveFirstPattern() {
		String expectedString = "i";
		Integer expectedInt = 5;
		assertEquals(expectedString, listStr.remove(expectedString));
		assertEquals(expectedInt, listInt.remove(expectedInt));

		String unexpectedString = "z";
		Integer unexpectedInt = 41;

		assertEquals(null, listStr.remove(unexpectedString));
		assertEquals(null, listInt.remove(unexpectedInt));

		int[] expectedIntArr = { 1, 2, 3, 4, 6, 7, 8 };
		String[] expectedStringArr = { "like", "java", "more", "then", "i", "like", "to", "sleep" };

		assertEquals(expectedStringArr.length, listStr.size());
		for (int i = 0; i < expectedStringArr.length; i++) {
			assertEquals(expectedStringArr[i], listStr.get(i));
		}

		assertEquals(expectedIntArr.length, listInt.size());
		for (int i = 0; i < expectedIntArr.length; i++) {
			assertEquals(expectedIntArr[i], listInt.get(i));
		}
	}

	@Test
	void testRemoveAllPatterns() {
		assertTrue(listStr.removeAll(patternListStr));
		assertTrue(listInt.removeAll(patternListInt));

		assertFalse(listStr.removeAll(antiPatternListStr));
		assertFalse(listInt.removeAll(antiPatternListInt));

		int[] expectedIntArr = { 2, 3, 4, 6, 7 };
		String[] expectedStringArr = { "like", "java", "then", "like", "to" };
		assertEquals(expectedStringArr.length, listStr.size());
		for (int i = 0; i < expectedStringArr.length; i++) {
			assertEquals(expectedStringArr[i], listStr.get(i));
		}

		assertEquals(expectedIntArr.length, listInt.size());
		for (int i = 0; i < expectedIntArr.length; i++) {
			assertEquals(expectedIntArr[i], listInt.get(i));
		}
	}

	@Test
	void testContains() {
		String expectedString = "i";
		Integer expectedInt = 5;
		assertTrue(listStr.contains(expectedString));
		assertTrue(listInt.contains(expectedInt));

		String unexpectedString = "z";
		Integer unexpectedInt = 41;
		assertFalse(listStr.contains(unexpectedString));
		assertFalse(listInt.contains(unexpectedInt));
	}

	@Test
	void testRetainAll() {

		assertFalse(listStr.retainAll(listStr));
		assertFalse(listInt.retainAll(listInt));

		assertTrue(listStr.retainAll(patternListStr));
		assertTrue(listInt.retainAll(patternListInt));

		String[] expectedStringArr = { "i", "more", "i", "sleep" };
		int[] expectedIntArr = { 1, 5, 8 };

		assertEquals(expectedStringArr.length, listStr.size());
		assertEquals(expectedIntArr.length, listInt.size());

		for (int i = 0; i < listStr.size(); i++) {
			assertEquals(expectedStringArr[i], listStr.get(i));
		}

		for (int i = 0; i < listInt.size(); i++) {
			assertEquals(expectedIntArr[i], listInt.get(i));
		}

		assertTrue(listStr.retainAll(antiPatternListStr));
		assertTrue(listInt.retainAll(antiPatternListInt));

		assertEquals(0, listStr.size());
		assertEquals(0, listInt.size());

	}
}
