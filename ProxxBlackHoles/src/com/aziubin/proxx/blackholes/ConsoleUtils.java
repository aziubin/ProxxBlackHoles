package com.aziubin.proxx.blackholes;

import java.util.Scanner;

public class ConsoleUtils {

	public static void printMessage(String message, Object... args) {
		System.out.print(MsgFmtBundle.INST.format(message, args));
	}
	
    public static int getIntFromUi(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            scanner.next();
            throw new IllegalArgumentException(MsgFmtBundle.INST.format("UNEXPECTED_INPUT"));
        }
        return scanner.nextInt();
    }
	
}
