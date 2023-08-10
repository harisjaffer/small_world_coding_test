package com.smallworld;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;

import com.smallworld.analyzer.TransactionAnalyzer;

public class Main {

		public static void main(String[] args) {
			// Specify the path to your JSON file
			String jsonFilePath = "transactions.json";

			try {
				// Read the JSON data from the file
				String jsonData = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

				// Parse the JSON data into a JSONArray
				JSONArray transactionsJsonArray = new JSONArray(jsonData);

				// Instantiate TransactionAnalyzer and analyze transactions
				TransactionAnalyzer transactionAnalyzer = new TransactionAnalyzer(transactionsJsonArray);
				transactionAnalyzer.analyzeTransactions();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
