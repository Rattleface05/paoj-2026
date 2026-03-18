package com.pao.laboratory03.exercise.exceptions;

import com.pao.laboratory03.exceptions.InvalidAgeException;

/**
 * 3. exception/InvalidStudentException.java — EXCEPȚIE CUSTOM
 *    - extends RuntimeException
 *    - Constructor cu String message → super(message)
 *
 **/

public class InvalidStudentException extends RuntimeException{
    public InvalidStudentException(String message){ super(message);}
}
