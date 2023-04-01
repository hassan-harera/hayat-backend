#!/bin/bash
ab -n 10 -c 10 -v 4 http://localhost:8080/api/v1/cities/1
