package com.gustavodscruz.livranada.service.contracts;

public interface IPlan<I, O> {
    public O execute(I request);
}
