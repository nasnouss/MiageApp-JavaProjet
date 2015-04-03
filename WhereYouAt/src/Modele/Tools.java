package Modele;

import java.awt.Color;
import java.util.Random;

public class Tools {

	public static String randomIp() {
		String ip = null;

		int part1 = random();
		int part2 = random();
		int part3 = random();
		int part4 = random();

		if (part1 == 0 || part1 == 10 || part1 == 127 || part1 == 224
				|| part1 == 240 || part1 >= 224) {
			while (part1 == 0 || part1 == 10 || part1 == 127 || part1 == 224
					|| part1 == 240) {
				System.out
						.println("part1 == 0 || part1 == 10 || part1 == 127 || part1 == 224 || part1 == 240 -- valeur :"
								+ part1);
				part1 = random();
			}

		} else if (part1 == 100) { // 100.[64...127]
			while (part2 <= 127 && part2 >= 64) {
				System.out.println("100.64-127");
				part2 = random();

			}
		} else if (part1 == 169 && part2 == 254) { // 169.254
			System.out.println("169.254");
			part2 = random();
		} else if (part1 == 172) { // 172.[16...31]

			while (part2 <= 31 && part2 >= 16) {
				System.out.println("172.16-31");
				part2 = random();

			}
		} else if (part1 == 192) {
			while (part2 == 0 && part3 == 2) { // 192.0.2.[0...255]
				System.out.println("-------192.0.2");
				part3 = random();
			}

			while (part2 == 0 && part3 == 0) { // 192.0.0.[0...255]
				System.out.println("--------192.0.0.0-255");
				part3 = random();
			}

			while (part2 == 88 && part3 == 99) { // 192.88.99.[0...255]
				System.out.println("--------192.88.99.[0...255]");
				part3 = random();
			}

			while (part2 == 168) { // 192.168.[0...255]
				System.out
						.println("----------192.168.[0...255] valeur" + part2);
				part2 = random();

			}

		} else if (part1 == 198) {
			while (part2 == 18 || part2 == 19) {
				part2 = random();

			}

			while (part2 == 51 && part3 == 100) {
				part3 = random();

			}

		} else if (part1 == 203) {
			while (part2 == 0 && part3 == 113) {
				part3 = random();
			}

		}

		System.out.println(part1 + "." + part2 + "." + part3 + "." + part4);
		ip = part1 + "." + part2 + "." + part3 + "." + part4;

		return ip;
	}

	public static int random() {
		int Max = 255;
		int Min = 0;
		return Min + (int) (Math.random() * ((Max - Min) + 1));
	}

	public static Color setCouleur() {
		Color randomColor;

		Random rand = new Random();

		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();

		randomColor = new Color(r, g, b);

		return randomColor;
	}
}
