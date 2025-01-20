package com.exa.android.finddog.data.mappers

import com.exa.android.finddog.data.local.DogEntity
import com.exa.android.finddog.data.remote.DogDto
import com.exa.android.finddog.domain.Dog
import java.util.UUID

fun DogDto.toDogEntity() : DogEntity{
    return DogEntity(
        imageUrl = message!!
    )
}

fun DogEntity.toDog() : Dog {
    return Dog(
        imageUrl = imageUrl
    )
}

fun List<DogEntity>.toDogList(): List<Dog> {
    return this.map { it.toDog() }
}