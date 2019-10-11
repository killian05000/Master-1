cmake_minimum_required(VERSION 3.10)
project(tp3_glpk)

set(CMAKE_CXX_STANDARD 17)

add_executable(tp3_glpk main.cpp)

target_link_libraries(tp3_glpk glpk)
