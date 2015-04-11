package Modele;

import java.awt.Color;
import java.net.UnknownHostException;
import java.util.Random;

import Main.Menu;

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

	public static void trancheIp(String ip1, String ip2,int id)
			throws UnknownHostException {
		int part1;
		int part2;
		int part3;
		int part4;
		String ip = ip1;
		String[] partIp1 = ip1.split("\\.");
		int bool = 0;
		Menu m = Menu.getInstance( );
		if (ip1.equals(ip2)) {
			System.out.println("L'ip de debut est identique à l'ip de fin");
		} else {
			if(id!=0){
				id++;
			}
			m.lancerThread(id, ip1);
			id++;

			while (!ip.equals(ip2)) {

				String[] parts = ip.split("\\.");

				if (parts.length != 4) {
					throw new IllegalArgumentException();
				} else {
					part1 = Integer.parseInt(parts[0]);
					part2 = Integer.parseInt(parts[1]);
					part3 = Integer.parseInt(parts[2]);
					part4 = Integer.parseInt(parts[3]);

					if (bool == 0 && part4 < 255) {
						part4++;
						ip = part1 + "." + part2 + "." + part3 + "." + part4;
						bool = 1;

					} else if (bool == 0 && part3 < 255) {
						part3++;
						if (part4 == 255 && (!partIp1[3].equals("255"))) {
							part4 = 0;
						}
						ip = part1 + "." + part2 + "." + part3 + "." + part4;

						bool = 1;

					} else if (bool == 0 && part2 < 255) {
						part2++;
						if (part3 == 255 && (!partIp1[2].equals("255"))) {
							part3 = 0;
						}
						ip = part1 + "." + part2 + "." + part3 + "." + part4;
						bool = 1;
					} else if (bool == 0 && part1 < 255) {
						part1++;
						if (part2 == 255 && (!partIp1[1].equals("255"))) {
							part2 = 0;
						}
						ip = part1 + "." + part2 + "." + part3 + "." + part4;
						bool = 1;
					}

				}
				bool = 0;
				m.lancerThread(id,ip);
				id++;
			}

		}

	}
}
