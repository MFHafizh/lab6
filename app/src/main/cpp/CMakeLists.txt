cmake_minimum_required(VERSION 3.6.0)

add_library(multiply
    SHARED

        multiply.c)

# Include libraries needed for lib
target_link_libraries(multiply
        android
        log)
