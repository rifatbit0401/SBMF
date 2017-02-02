package com.rifat.sbmf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import japa.parser.ParseException;

public class Main {

	public static void main(String[] args) throws ParseException, IOException, InterruptedException {
		Controller controller = new Controller();
		String codeBasePath = "E:/BSSE Program/3rd semister BIT program/OOP-2/workspace/Calculator2/src";
		controller.start(codeBasePath);
	}
}
