#!/bin/bash
ab -n 25 -p ./data.json -v 4 http://localhost:8080/api/v1/auth/login
