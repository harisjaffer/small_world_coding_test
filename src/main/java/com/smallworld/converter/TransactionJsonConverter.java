package com.smallworld.converter;

import org.json.JSONObject;

import com.smallworld.entity.Transaction;

public class TransactionJsonConverter {

    public static Transaction convertJsonToTransaction(JSONObject transactionJsonObject) {
        int mtn = transactionJsonObject.getInt("mtn");
        double amount = transactionJsonObject.getDouble("amount");
        String senderFullName = transactionJsonObject.getString("senderFullName");
        int senderAge = transactionJsonObject.getInt("senderAge");
        String beneficiaryFullName = transactionJsonObject.getString("beneficiaryFullName");
        int beneficiaryAge = transactionJsonObject.getInt("beneficiaryAge");
        Integer issueId = transactionJsonObject.isNull("issueId") ? null : transactionJsonObject.getInt("issueId");
        boolean issueSolved = transactionJsonObject.getBoolean("issueSolved");
        String issueMessage = transactionJsonObject.isNull("issueMessage") ? null : transactionJsonObject.getString("issueMessage");

        return new Transaction(mtn, amount, senderFullName, senderAge, beneficiaryFullName,
                beneficiaryAge, issueId, issueSolved, issueMessage);
    }

	
}
