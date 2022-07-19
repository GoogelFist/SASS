package com.example.sass.presentation.screens

interface EventHandler<E> {

    fun obtainEvent(event: E)
}