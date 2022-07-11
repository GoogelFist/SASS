package com.example.sass.data

class IncorrectTokenException(message: String) : Throwable(message)

class BlankLoginException() : Throwable()
class BlankPasswordException() : Throwable()

class InvalidateLoginException() : Throwable()
class InvalidatePasswordException() : Throwable()