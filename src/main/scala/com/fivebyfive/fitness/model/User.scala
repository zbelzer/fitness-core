package com.fivebyfive.fitness.model

import java.util.UUID

case class User(
    id: UUID,
    firstName: String,
    lastName: String,
    login: String,
    email: String
)
