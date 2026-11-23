package com.saad.users;

class ConflictException extends RuntimeException { ConflictException() { super("Record already exists."); } }
