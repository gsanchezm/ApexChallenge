package com.apex.core;

public interface IBase {
    <T extends IBase> T getInstance(Class<T> classToInstantiate);

    <T extends IBase> T as(Class<T> classToInstantiate);
}
