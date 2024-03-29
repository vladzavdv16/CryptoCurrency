package com.cryprocurrency.data.repository

import com.cryprocurrency.data.repository.CoinsRepo
import com.cryprocurrency.data.repository.WalletsRepo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.cryprocurrency.data.model.Currency
import com.cryprocurrency.data.model.Transaction
import com.cryprocurrency.data.model.Wallet
import io.reactivex.Observable
import javax.inject.Inject

class WalletsRepoImpl @Inject constructor(
    private val coinsRepo: CoinsRepo
) : WalletsRepo {

    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun wallet(currency: Currency): Observable<List<Wallet>> =
        Observable.create<QuerySnapshot> { emitter ->
            val addSnapshotListener =
                firestore.collection("wallets")
                    .orderBy("created_at", Query.Direction.ASCENDING)
                    .addSnapshotListener { value, error ->
                    if (emitter.isDisposed) return@addSnapshotListener
                    if (value != null) {
                        emitter.onNext(value)
                    } else if (error != null) {
                        emitter.tryOnError(error)
                    }
                }
            emitter.setCancellable(addSnapshotListener::remove)
        }
            .map { snapshots ->
                snapshots.documents
            }
            .switchMapSingle { documents ->
                Observable
                    .fromIterable(documents)
                    .flatMapSingle { document ->
                        coinsRepo.coin(currency, document.getLong("coinId"))
                            .map { coin ->
                                Wallet(document.id,
                                coin,
                                document.getDouble("balance"))
                            }
                    }.toList()
            }


    override fun transaction(wallet: Wallet): Observable<List<Transaction>> =
        Observable.create<QuerySnapshot> { emitter ->
            val addSnapshotListener =
                firestore
                    .collection("wallets")
                    .document(wallet.id)
                    .collection("transaction")
                    .addSnapshotListener { value, error ->
                        if (emitter.isDisposed) return@addSnapshotListener
                        if (value != null) {
                            emitter.onNext(value)
                        } else if (error != null) {
                            emitter.tryOnError(error)
                        }
                    }
            emitter.setCancellable(addSnapshotListener::remove)
        }.map(QuerySnapshot::getDocuments)
            .switchMapSingle { document ->
                Observable
                    .fromIterable(document)
                    .map { documents ->
                        documents.getDate("created_at")?.let {
                            Transaction(
                                documents.id,
                                wallet.coin,
                                documents.getDouble("amount"),
                                it
                            )
                        }
                    }.toList()
            }
}