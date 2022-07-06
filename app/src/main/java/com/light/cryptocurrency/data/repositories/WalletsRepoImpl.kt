package com.light.cryptocurrency.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.light.cryptocurrency.data.model.Currency
import com.light.cryptocurrency.data.model.Transaction
import com.light.cryptocurrency.data.model.Wallet
import io.reactivex.Observable
import javax.inject.Inject

class WalletsRepoImpl @Inject constructor(
    private val coinsRepo: CoinsRepo
) : WalletsRepo {

    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun wallet(currency: Currency): Observable<List<Wallet>> =
        Observable.create<QuerySnapshot> { emitter ->
            val addSnapshotListener =
                firestore.collection("wallets").addSnapshotListener { value, error ->
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
                    .switchMapSingle { document ->
                        coinsRepo.coin(currency, document.getLong("coinId"))
                            .map { coin ->
                                Wallet(document.id,
                                coin,
                                document.getDouble("balance"))
                            }
                    }.toList()
            }



    override fun transaction(wallet: Wallet): Observable<List<Transaction>> {
        TODO("Not yet implemented")
    }
}