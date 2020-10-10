package de.datlag.skeleton.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecyclerItem(val text: String) : Parcelable
