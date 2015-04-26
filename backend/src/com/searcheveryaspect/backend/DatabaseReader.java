package com.searcheveryaspect.backend;


/**
 * 
 */
public interface DatabaseReader<I, O> {

  O read(I input);
}
