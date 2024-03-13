package br.com.rocketseat.main.exceptions;

public class JobNotFoundException extends RuntimeException{

    public JobNotFoundException() {
        super("Job not found.");
    }
}
