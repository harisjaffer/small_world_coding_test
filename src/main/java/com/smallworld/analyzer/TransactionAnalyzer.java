package com.smallworld.analyzer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.json.JSONArray;

import com.smallworld.TransactionDataFetcher;
import com.smallworld.entity.Transaction;

public class TransactionAnalyzer {
	
	private final TransactionDataFetcher dataFetcher;

    public TransactionAnalyzer(JSONArray transactionsJsonArray) {
        dataFetcher = new TransactionDataFetcher(transactionsJsonArray);
    }

    public void analyzeTransactions() {
    	
        double totalAmount = dataFetcher.getTotalTransactionAmount();
        System.out.println("Total Transaction Amount: " + totalAmount);

        double totalAmountSentByTomShelby = dataFetcher.getTotalTransactionAmountSentBy("Tom Shelby");
        System.out.println("Total Amount Sent by Tom Shelby: " + totalAmountSentByTomShelby);

        double maxTransactionAmount = dataFetcher.getMaxTransactionAmount();
        System.out.println("Max Transaction Amount: " + maxTransactionAmount);

        long uniqueClientsCount = dataFetcher.countUniqueClients();
        System.out.println("Unique Clients Count: " + uniqueClientsCount);

        boolean hasOpenIssues = dataFetcher.hasOpenComplianceIssues("Tom Shelby");
        System.out.println("Has Open Compliance Issues for Tom Shelby: " + hasOpenIssues);

        // Get transactions by beneficiary name
        Map<String, List<Transaction>> transactionsByBeneficiary = dataFetcher.getTransactionsByBeneficiaryName();
        System.out.println("Transactions by Beneficiary Name:");
        transactionsByBeneficiary.forEach((beneficiary, transactions) -> {
            System.out.println(beneficiary + ": " + transactions.size() + " transactions");
        });

        // Get unsolved issue IDs
        Set<Integer> unsolvedIssueIds = dataFetcher.getUnsolvedIssueIds();
        System.out.println("Unsolved Issue IDs: " + unsolvedIssueIds);

        // Get all solved issue messages
        List<String> solvedIssueMessages = dataFetcher.getAllSolvedIssueMessages();
        System.out.println("Solved Issue Messages: " + solvedIssueMessages);

        // Get top 3 transactions by amount
        List<Transaction> top3Transactions = dataFetcher.getTop3TransactionsByAmount();
        System.out.println("Top 3 Transactions by Amount:");
        for (int i = 0; i < top3Transactions.size(); i++) {
            Transaction transaction = top3Transactions.get(i);
            System.out.println((i + 1) + ". " + transaction);
        }

        // Get top sender
        Optional<String> topSender = dataFetcher.getTopSender();
        topSender.ifPresent(sender -> System.out.println("Top Sender: " + sender));
    }

    }