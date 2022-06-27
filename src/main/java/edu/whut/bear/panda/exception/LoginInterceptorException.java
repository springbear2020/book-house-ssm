package edu.whut.bear.panda.exception;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 13:05 Monday
 */
public class LoginInterceptorException extends Exception {

    private String msg;

    public LoginInterceptorException() {
    }

    public LoginInterceptorException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}