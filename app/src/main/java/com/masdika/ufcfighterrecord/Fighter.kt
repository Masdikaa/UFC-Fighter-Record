package com.masdika.ufcfighterrecord

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fighter(
    val image: String,
    val name: String,
    val division: String,
    val about: String,
    val fighterWin: Int,
    val fighterLoss: Int,
    val fighterDraw: Int,
    val strikePercentage: Int,
    val takeDownPercentage: Int,
    val winByKnockOut: Int,
    val winBySubmission: Int,
    val fighterTitle: String
) : Parcelable
