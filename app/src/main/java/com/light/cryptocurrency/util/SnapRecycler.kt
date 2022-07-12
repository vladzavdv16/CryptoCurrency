package com.light.cryptocurrency.util

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.SnapHelper
import io.reactivex.Observable


fun onSnap(rv: RecyclerView, helper: SnapHelper): Observable<Int> {
    return Observable.create { emitter ->
        val listener = object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    val snapView = helper.findSnapView(rv.layoutManager);
                    if (snapView != null) {
                        val holder = rv.findContainingViewHolder(snapView);
                        if (holder != null) {
                            emitter.onNext(holder.adapterPosition)
                        }
                    }
                }
            }
        }
        emitter.setCancellable {
            rv.removeOnScrollListener(listener)
        }
        rv.addOnScrollListener(listener)
    }
}




