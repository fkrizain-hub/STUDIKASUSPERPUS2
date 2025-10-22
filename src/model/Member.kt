package model

import repository.Identifiable

data class Member (
 override val id: String,
val name: String,
var tier:Tier,
var Activestatus: Boolean = true ): Identifiable