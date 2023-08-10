package com.smallworld;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.smallworld.converter.TransactionJsonConverter;
import com.smallworld.entity.Transaction;

public class TransactionDataFetcher {

	private List<Transaction> transactions;

	public TransactionDataFetcher(JSONArray transactionsJsonArray) {
		this.transactions = new ArrayList<>();
		for (int i = 0; i < transactionsJsonArray.length(); i++) {
			JSONObject transactionJsonObject = transactionsJsonArray.getJSONObject(i);
			Transaction transaction = TransactionJsonConverter.convertJsonToTransaction(transactionJsonObject);
			transactions.add(transaction);
		}
	}

	/**
	 * Returns the sum of the amounts of all transactions
	 */

	public double getTotalTransactionAmount() {
		double totalAmount = 0.0;

		for (Transaction transaction : transactions) {
			totalAmount += transaction.getAmount();
		}

		return totalAmount;
	}

	/**
	 * Returns the sum of the amounts of all transactions sent by the specified
	 * client
	 */
	public double getTotalTransactionAmountSentBy(String senderFullName) {
		return transactions.stream().filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
				.mapToDouble(Transaction::getAmount).sum();
	}

	/**
	 * Returns the highest transaction amount
	 */
	public double getMaxTransactionAmount() {
		return transactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0.0); // Default value in case
																							// there are no transactions
	}

	/**
	 * Counts the number of unique clients that sent or received a transaction
	 */
	public long countUniqueClients() {
		Set<String> uniqueClients = new HashSet<>();

		for (Transaction transaction : transactions) {
			uniqueClients.add(transaction.getSenderFullName());
			uniqueClients.add(transaction.getBeneficiaryFullName());
		}

		return uniqueClients.size();
	}

	/**
	 * Returns whether a client (sender or beneficiary) has at least one transaction
	 * with a compliance issue that has not been solved
	 */
	public boolean hasOpenComplianceIssues(String clientFullName) {
		return transactions.stream()
				.anyMatch(transaction -> (transaction.getSenderFullName().equals(clientFullName)
						|| transaction.getBeneficiaryFullName().equals(clientFullName))
						&& !transaction.isIssueSolved());
	}

	/**
	 * Returns all transactions indexed by beneficiary name
	 */
	public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
		Map<String, List<Transaction>> transactionsByBeneficiary = new HashMap<>();

		for (Transaction transaction : transactions) {
			String beneficiaryName = transaction.getBeneficiaryFullName();
			transactionsByBeneficiary.computeIfAbsent(beneficiaryName, k -> new ArrayList<>()).add(transaction);
		}

		return transactionsByBeneficiary;
	}

	/**
	 * Returns the identifiers of all open compliance issues
	 */
	public Set<Integer> getUnsolvedIssueIds() {
		Set<Integer> unsolvedIssueIds = new HashSet<>();

		for (Transaction transaction : transactions) {
			if (!transaction.isIssueSolved() && transaction.getIssueId() != null) {
				unsolvedIssueIds.add(transaction.getIssueId());
			}
		}

		return unsolvedIssueIds;
	}

	/**
	 * Returns a list of all solved issue messages
	 */
	public List<String> getAllSolvedIssueMessages() {
		List<String> solvedIssueMessages = new ArrayList<>();

		for (Transaction transaction : transactions) {
			if (transaction.isIssueSolved() && transaction.getIssueMessage() != null) {
				solvedIssueMessages.add(transaction.getIssueMessage());
			}
		}

		return solvedIssueMessages;
	}

	/**
	 * Returns the 3 transactions with highest amount sorted by amount descending
	 */
	public List<Transaction> getTop3TransactionsByAmount() {
		return transactions.stream().sorted(Comparator.comparing(Transaction::getAmount).reversed()).limit(3)
				.collect(Collectors.toList());
	}

	/**
	 * Returns the sender with the most total sent amount
	 */
	public Optional<String> getTopSender() {
		Map<String, Double> senderTotalAmounts = transactions.stream().collect(Collectors
				.groupingBy(Transaction::getSenderFullName, Collectors.summingDouble(Transaction::getAmount)));

		Optional<Map.Entry<String, Double>> topSenderEntry = senderTotalAmounts.entrySet().stream()
				.max(Map.Entry.comparingByValue());

		return topSenderEntry.map(Map.Entry::getKey);
	}

}
