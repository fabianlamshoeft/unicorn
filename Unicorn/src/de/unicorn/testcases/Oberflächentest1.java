package de.unicorn.testcases;

import org.junit.jupiter.api.Test;

import de.unicorn.view.Chat;

class Oberflächentest1 {

	@Test
	void test() {
		Chat testChat = new Chat();
		testChat.newScreen();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
