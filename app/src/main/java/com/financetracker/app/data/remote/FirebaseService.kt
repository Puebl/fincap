package com.financetracker.app.data.remote

import com.financetracker.app.data.model.Transaction
import com.financetracker.app.data.model.Category
import com.financetracker.app.data.model.Budget
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseService @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    
    private fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
    
    suspend fun syncTransactions(transactions: List<Transaction>) {
        val userId = getCurrentUserId() ?: return
        
        val batch = firestore.batch()
        val collection = firestore.collection("users").document(userId).collection("transactions")
        
        transactions.forEach { transaction ->
            val docRef = collection.document(transaction.id.toString())
            batch.set(docRef, transaction)
        }
        
        batch.commit().await()
    }
    
    suspend fun syncCategories(categories: List<Category>) {
        val userId = getCurrentUserId() ?: return
        
        val batch = firestore.batch()
        val collection = firestore.collection("users").document(userId).collection("categories")
        
        categories.forEach { category ->
            val docRef = collection.document(category.id.toString())
            batch.set(docRef, category)
        }
        
        batch.commit().await()
    }
    
    suspend fun syncBudgets(budgets: List<Budget>) {
        val userId = getCurrentUserId() ?: return
        
        val batch = firestore.batch()
        val collection = firestore.collection("users").document(userId).collection("budgets")
        
        budgets.forEach { budget ->
            val docRef = collection.document(budget.id.toString())
            batch.set(docRef, budget)
        }
        
        batch.commit().await()
    }
    
    suspend fun getTransactions(): List<Transaction> {
        val userId = getCurrentUserId() ?: return emptyList()
        
        val snapshot = firestore.collection("users")
            .document(userId)
            .collection("transactions")
            .get()
            .await()
        
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(Transaction::class.java)
        }
    }
    
    suspend fun getCategories(): List<Category> {
        val userId = getCurrentUserId() ?: return emptyList()
        
        val snapshot = firestore.collection("users")
            .document(userId)
            .collection("categories")
            .get()
            .await()
        
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(Category::class.java)
        }
    }
    
    suspend fun getBudgets(): List<Budget> {
        val userId = getCurrentUserId() ?: return emptyList()
        
        val snapshot = firestore.collection("users")
            .document(userId)
            .collection("budgets")
            .get()
            .await()
        
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(Budget::class.java)
        }
    }
} 