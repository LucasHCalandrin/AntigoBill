package com.example.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Integrante(
    var nome: String,
    var gastou: String,
    var comprou: String
): Parcelable